package com.example.pay2park;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class RegisterParkingActivity extends AppCompatActivity {

    private EditText mlocality;
    private EditText mdetailadd;
    private EditText mparkingno;
    private EditText mprice;

    private Button btninsert , viewdatabtn;

    FirebaseDatabase firebaseDatabase;

    // creating a variable for our Database
    // Reference for Firebase.
//    DatabaseReference databaseReference;

    DatabaseReference database_address;
    // creating a variable for
    // our object class
    Address address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_parking);


        mlocality= findViewById(R.id.registerlocality);
        mdetailadd= findViewById(R.id.registerparkingadd);
        mparkingno= findViewById(R.id.registerparkingno);
        mprice=findViewById(R.id.registerprice);

        btninsert= findViewById(R.id.proceed_from_address_btn);
        viewdatabtn=(Button) findViewById(R.id.viewdata_saved);

        // below line is used to get the
        // instance of our FIrebase database.
//        firebaseDatabase = FirebaseDatabase.getInstance();
//
//        // below line is used to get reference for our database.
//        databaseReference = firebaseDatabase.getReference("Address");
        database_address=FirebaseDatabase.getInstance().getReference();

        btninsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertData();
            }
        });

        viewdatabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterParkingActivity.this , Addresslist.class));
                finish();
            }
        });



    }

    private void InsertData() {
        String locality_add= mlocality.getText().toString().trim();
        String address_full=mdetailadd.getText().toString().trim();
        String parkingno= mparkingno.getText().toString().trim();
        String price= mprice.getText().toString().trim();
        String id = database_address.push().getKey();

        if(locality_add.isEmpty()){
            mlocality.setError("Locality name required");
            mlocality.requestFocus();
        }

        if(address_full.isEmpty()){
            mdetailadd.setError("Tower name required");
            mdetailadd.requestFocus();
        }

        if(parkingno.isEmpty()){
            mparkingno.setError("Parking number is required");
            mparkingno.requestFocus();
        }

        if(price.isEmpty()){
            mprice.setError("Price is required");
            mprice.requestFocus();
        }

        Addressdata addressdata = new Addressdata(locality_add , address_full, parkingno, price);
        database_address.child("Parking_address").child(id).setValue(addressdata)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterParkingActivity.this, "Address data inserted", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}