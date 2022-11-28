package com.example.pay2park.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.pay2park.Models.statusmodel;
import com.example.pay2park.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SellerRegistrationDoneActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;

    DatabaseReference dbref;
    String value;

    com.example.pay2park.Models.statusmodel statusmodel;
    String status="available";

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SellerRegistrationDoneActivity.this, UserAdminSelection.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_registration_done);

        firebaseDatabase=FirebaseDatabase.getInstance();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("key");
            //The key argument here must match that used in the other activity
        }

        statusmodel= new statusmodel();
        firebaseDatabase= FirebaseDatabase.getInstance();
        dbref= firebaseDatabase.getReference("Parking_address").child(value).child("Status");

        statusmodel.setStatus(status);
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(SellerRegistrationDoneActivity.this, "Status Updated", Toast.LENGTH_SHORT).show();
                dbref.setValue(statusmodel);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}