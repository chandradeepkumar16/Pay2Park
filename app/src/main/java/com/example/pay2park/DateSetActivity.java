package com.example.pay2park;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateSetActivity extends AppCompatActivity {
    //initialize variable
    EditText tvtimer1, tvtimer2;
    int t1Hour, t1Minute, t2Hour, t2Minute;
    TextView fprice;
    TextView ttime;
    Date d1=null;
    Date d2=null;
    long a, b=12345678910L;
    String price;
    int calcprice;
    private Button timeupload;

    FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;

    DatabaseReference databaseReference;
    Buytime buytime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_date_set);
         tvtimer1=findViewById(R.id.tv_timer1);
        tvtimer2=findViewById(R.id.tv_timer2);
        fprice=findViewById(R.id.pricedisplay);
        ttime=findViewById(R.id.difftimedisplay);

        firebaseDatabase= FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();

        databaseReference=firebaseDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Buyer Timing");

        buytime= new Buytime();

        timeupload=(Button)findViewById(R.id.timeupload);

        timeupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });


        Addressdata addressdata= (Addressdata) getIntent().getSerializableExtra("price");
        Toast.makeText(this, ""+addressdata.getPrice(), Toast.LENGTH_SHORT).show();
        price= addressdata.getPrice();
        calcprice=Integer.parseInt(price);


        tvtimer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog= new TimePickerDialog(DateSetActivity.this,android.R.style.Theme_Holo_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
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
                            tvtimer1.setText(f12Hours.format(date));
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

        tvtimer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //initialize

TimePickerDialog timePickerDialog= new TimePickerDialog(DateSetActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
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
            tvtimer2.setText(f12Hours.format(date));
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
        String starttime= tvtimer1.getText().toString();
        String stoptime= tvtimer2.getText().toString();
        addDatatoFirebase(starttime, stoptime);

    }

    private void addDatatoFirebase(String starttime, String stoptime) {
        buytime.setStarttime(starttime);
        buytime.setEndtime(stoptime);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.setValue(buytime);
                Toast.makeText(DateSetActivity.this, "Data added", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DateSetActivity.this, "Failed to add data, try again", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void showtimeduration() {
        if(d1!=null && d2!=null && tvtimer1!=null && tvtimer2!=null) {
            Toast.makeText(this, ""+(b-a), Toast.LENGTH_SHORT).show();
            long diff = b-a;
            long difftotalhours = diff / (60 * 60 * 1000) % 60;
            if(difftotalhours<2){
                ttime.setText(+difftotalhours + " hour");
                fprice.setText("₹"+calcprice*difftotalhours);

            }
            else {
                ttime.setText(+difftotalhours + " hours");
                fprice.setText("₹"+calcprice*difftotalhours);
            }
        }
    }
}