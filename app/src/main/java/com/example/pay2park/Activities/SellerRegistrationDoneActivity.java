package com.example.pay2park.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pay2park.Models.statusmodel;
import com.example.pay2park.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SellerRegistrationDoneActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference dbref;
    private Button register;
    private String value ;
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

        register= findViewById(R.id.parkingsubmit);
        statusmodel= new statusmodel();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("key");
            //The key argument here must match that used in the other activity
        }
        firebaseDatabase= FirebaseDatabase.getInstance();

        dbref= firebaseDatabase.getReference("Parking_address").child(value).child("Status");

        register.setOnClickListener((v -> {
            dbref.child("status").setValue(status).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    startActivity(new Intent(SellerRegistrationDoneActivity.this, UserAdminSelection.class));
                }
            });
        }));

    }
}