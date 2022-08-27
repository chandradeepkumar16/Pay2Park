package com.example.pay2park;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class PayNowActivity extends AppCompatActivity {
    TextView nameofuser , showcost , showhours;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    private String timetaken ;
    private String costtaken;
    private String starttiming;
    private String endtimimg;
    private TextView buyertiming;

    Button paynowbtn;

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

        firebaseDatabase=FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();

        databaseReference=firebaseDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Details").child("firstname");



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

            //The key argument here must match that used in the other activity
        }


        showhours.setText(timetaken + "hours");
        showcost.setText("₹"+costtaken+"/");
        buyertiming.setText(starttiming+" - "+endtimimg);


        paynowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PayNowActivity.this, TicketGenerationActivity.class));
            }
        });


    }
}