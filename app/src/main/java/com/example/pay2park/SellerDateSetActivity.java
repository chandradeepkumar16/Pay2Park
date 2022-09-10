package com.example.pay2park;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SellerDateSetActivity extends AppCompatActivity {

    EditText sellertvtimer1, sellertvtimer2;
    int t1Hour, t1Minute, t2Hour, t2Minute;
    TextView ttime;
    TextView fprice;

    Date d1=null;
    Date d2=null;
    long a, b=12345678910L;

    private Button sellersubmit;

    FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;


    // creating a variable for our Database
    // Reference for Firebase.
//    DatabaseReference databaseReference;

    DatabaseReference database_sellertime;
    // creating a variable for
    // our object class

    Sellertimedata sellertimedata;
    private String value ;
    private String sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_seller_date_set);
        sellertvtimer1=findViewById(R.id.seller_tv_timer1);
        sellertvtimer2=findViewById(R.id.seller_tv_timer2);
        ttime=findViewById(R.id.sellerdifftimedisplay);
        fprice=findViewById(R.id.sellerpricedisplay);

        sellersubmit=findViewById(R.id.sellersubmitbtn);

        firebaseDatabase= FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        sellertimedata= new Sellertimedata();

//        detailsModel= new DetailsModel();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("key");
            sp= extras.getString("sellingprice");
            //The key argument here must match that used in the other activity
        }
        fprice.setText(sp);
        database_sellertime=firebaseDatabase.getReference("Parking_address").child(value).child("Seller Timing");



        // database_sellertime= firebaseDatabase.getReference("Parking_address");
        //        Bundle extras1 = getIntent().getExtras();
        //        if (extras1 != null) {
        //            sp = extras1.getString("sellingprice");
        //            //The key argument here must match that used in the other activity
        //        }


        sellersubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });


        sellertvtimer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog= new TimePickerDialog(SellerDateSetActivity.this,android.R.style.Theme_Holo_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        //inititalize hour and minute

                        t1Hour= i; //hourofday
                        t1Minute= i1; //minute
                        String time= t1Hour+ ":" +t1Minute;
                        //initialize 24 hours format
                        SimpleDateFormat f24ours= new SimpleDateFormat( "HH:mm");
                        try{
                            Date date= f24ours.parse(time);
                            d1=date;
                            a=d1.getTime();
                            SimpleDateFormat f12Hours = new SimpleDateFormat(
                                    "hh:mm aa"
                            );
                            //set selected time on text view
                            sellertvtimer1.setText(f12Hours.format(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, 12, 0, false

                ) ;
                //set transparent background
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //display previous selected time
                timePickerDialog.updateTime(t1Hour,t1Minute);
                //show dialog
                timePickerDialog.show();
            }
        });

        sellertvtimer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //initialize

                TimePickerDialog timePickerDialog= new TimePickerDialog(SellerDateSetActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        //initialize hour and minute
                        t2Hour= i;
                        t2Minute=i1;
                        //store hour and minute in string
                        String time= t2Hour+ ":" +t2Minute;
                        //initialize 24 hours format
                        SimpleDateFormat f24ours= new SimpleDateFormat( "HH:mm");
                        try{
                            Date date= f24ours.parse(time);
                            d2=date;
                            b=d2.getTime();
                            SimpleDateFormat f12Hours = new SimpleDateFormat(
                                    "hh:mm aa"
                            );
                            //set selected time on text view
                            sellertvtimer2.setText(f12Hours.format(date));
                            showtimeduration();


                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, 12, 0, false

                ) ;

                //set transparent background
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //display previious selected time
                timePickerDialog.updateTime(t2Hour,t2Minute);
                //show dialog
                timePickerDialog.show();

            }
        });

    }

    private void upload() {
        String stime= sellertvtimer1.getText().toString();
        String etime= sellertvtimer2.getText().toString();
        InsertsellertimeData(stime, etime);


//        if(TextUtils.isEmpty(stime) || TextUtils.isEmpty(etime)){
//            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
//        }else{
//
//        }
    }


    private void InsertsellertimeData(String stime, String etime) {
        sellertimedata.setSellerstarttime(stime);
        sellertimedata.setSellerstoptime(etime);


        database_sellertime.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                database_sellertime.setValue(sellertimedata);
                Toast.makeText(SellerDateSetActivity.this, "Data added", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SellerDateSetActivity.this, SellerRegistrationDoneActivity.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SellerDateSetActivity.this, "Failed to add data, try again", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showtimeduration() {
        if(d1!=null && d2!=null && sellertvtimer1!=null && sellertvtimer2!=null) {
            long diff = b-a;
            long difftotalhours = diff / (60 * 60 * 1000) % 60;
            if(difftotalhours<2){
                ttime.setText(+difftotalhours + " hour");

            }
            else {
                ttime.setText(+difftotalhours + " hours");
            }
        }
    }
}