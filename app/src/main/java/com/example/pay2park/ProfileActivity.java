package com.example.pay2park;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class ProfileActivity extends AppCompatActivity {

    private TextView p_num , p_name , p_dlno , p_vhtype , p_lastname , topname ,topemail;
    private Button logout_btn;
    private ImageView walletimg , settingimg;
    FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;
    DatabaseReference dbref_pname , dbref_pnamel ,dbref_p_num , dbref_dlno , dbref_vhtype , dbref_email;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        p_num=findViewById(R.id.profile_num);
        p_name=findViewById(R.id.profile_name);
        p_dlno=findViewById(R.id.profile_dlno);
        p_vhtype=findViewById(R.id.profile_vehicletype);
        p_lastname=findViewById(R.id.profile_lastname);

        logout_btn=(Button)findViewById(R.id.logout);

        walletimg=(ImageView)findViewById(R.id.img_wallet);
        settingimg=(ImageView)findViewById(R.id.img_setting);

        topname=findViewById(R.id.top_name);
        topemail=findViewById(R.id.top_email);


        firebaseDatabase=FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();

        dbref_email=firebaseDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("email");
        dbref_pname=firebaseDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Details").child("firstname");
        dbref_pnamel=firebaseDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Details").child("lastname");
        dbref_p_num=firebaseDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Details").child("contact");
        dbref_dlno=firebaseDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Details").child("uploaddl");
        dbref_vhtype=firebaseDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Details").child("vehicletype");


        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Toast.makeText(ProfileActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ProfileActivity.this, LogInActivity.class));
            }
        });


        dbref_email.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String st = snapshot.getValue(String.class);
                topemail.setText(st);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        dbref_pname.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String st=snapshot.getValue(String.class);
                p_name.setText(st);
                topname.setText(st);


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        dbref_pnamel.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String st=snapshot.getValue(String.class);
                p_lastname.setText(st);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        dbref_p_num.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String st=snapshot.getValue(String.class);
                p_num.setText(st);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        dbref_dlno.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String st=snapshot.getValue(String.class);
                p_dlno.setText(st);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        dbref_vhtype.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String st=snapshot.getValue(String.class);
                p_vhtype.setText(st);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        walletimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProfileActivity.this, "Wallet Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        settingimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProfileActivity.this, "Setting clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }


}