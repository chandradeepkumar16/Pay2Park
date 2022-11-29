package com.example.pay2park.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pay2park.Models.Timeleft;
import com.example.pay2park.Models.parkingiduser;
import com.example.pay2park.Models.statusmodel;
import com.example.pay2park.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class PayNowActivity extends AppCompatActivity {

    TextView nameofuser , showcost , showhours, test;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, dbref, dbref1 , dbref_timeleft;

    FirebaseAuth mAuth;

    private String timetaken ;
    private String costtaken;
    private String starttiming;
    private String endtimimg;
    private TextView buyertiming;
    String id; //for parking id

    Calendar calendar;
    String currentTime="";
    int extratime;
    int minutes;

    int difference=0;
    int cur_t=0;

    Button paynowbtn;

    parkingiduser parkingid;
    com.example.pay2park.Models.statusmodel statusmodel;
    Timeleft tl;


    String status="booked";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pay_now);
        nameofuser=(TextView)findViewById(R.id.nameofuser);
        showcost=(TextView)findViewById(R.id.showcost);
        showhours=(TextView)findViewById(R.id.showhours);
        buyertiming=(TextView)findViewById(R.id.showtime);
        paynowbtn=(Button)findViewById(R.id.paynowbtn);

        parkingid= new parkingiduser();
        statusmodel= new statusmodel();
        tl= new Timeleft();

        firebaseDatabase=FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();

        databaseReference=firebaseDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Details").child("firstname");

        dbref= firebaseDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Booking_id");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String st=snapshot.getValue(String.class);
                nameofuser.setText(st);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        Bundle extras1 = getIntent().getExtras();
        if (extras1 != null) {
            timetaken = String.valueOf(extras1.get("totaltime"));
            costtaken= String.valueOf(extras1.get("totalprice"));
            starttiming= String.valueOf(extras1.get("begintime"));
            endtimimg=String.valueOf(extras1.get("endtime"));
            id= String.valueOf(extras1.get("key"));

            //The key argument here must match that used in the other activity
        }

        showhours.setText(timetaken + "hours");
        showcost.setText("₹"+costtaken+"/");
        buyertiming.setText(starttiming+" - "+endtimimg); //changesmade

        dbref1= firebaseDatabase.getReference("Parking_address").child(id).child("Status");
        dbref_timeleft= firebaseDatabase.getReference("Parking_address").child(id).child("timeleft");


        currentTime = new SimpleDateFormat( "HH:mm:ss", Locale.getDefault()).format(new Date());

        calendar=Calendar.getInstance();
        extratime = calendar.get(Calendar.HOUR_OF_DAY)+Integer.parseInt(timetaken);
        minutes = calendar.get(Calendar.MINUTE);

        difference = 24-extratime;
        if(difference<0) {
            cur_t=difference*(-1);
        }
        else{
            cur_t=extratime;
        }


        paynowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProgressDialog dialog = ProgressDialog.show(PayNowActivity.this, "", "Processing your Payment...",
                        true);

                dialog.show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        dialog.dismiss();

                    }
                }, 2000);


                updatestatus(status);
                insertidtodatabase(id);
                Intent p = new Intent(PayNowActivity.this, TicketGenerationActivity.class);
                p.putExtra("key", id);
                startActivity(p);

            }
        });


    }

    private void updatestatus(String status) {
        HashMap hashMap= new HashMap();
        hashMap.put("status", status);
        dbref1.updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(PayNowActivity.this, "Status changed to booked", Toast.LENGTH_SHORT).show();
            }
        });

//        statusmodel.setStatus(status);
//        dbref1.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Toast.makeText(PayNowActivity.this, "Status Updated", Toast.LENGTH_SHORT).show();
//                dbref1.setValue(statusmodel);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

//        tl.setTime(String.valueOf(cur_t + ":"+ minutes));

        tl.setTime(timetaken);
        dbref_timeleft.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                dbref_timeleft.setValue(tl);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void insertidtodatabase(String booking_id) {
        parkingid.setId(booking_id);
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dbref.setValue(parkingid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}