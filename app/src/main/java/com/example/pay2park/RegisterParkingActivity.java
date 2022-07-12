package com.example.pay2park;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterParkingActivity extends AppCompatActivity {

    private EditText mlocality;
    private EditText mdetailadd;
    private Button mAddAddressbtn;

    FirebaseDatabase firebaseDatabase;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;

    // creating a variable for
    // our object class
    Address address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_parking);
        mlocality= findViewById(R.id.registerlocality);
        mdetailadd= findViewById(R.id.registerparkingadd);
        mAddAddressbtn= findViewById(R.id.proceed_from_address_btn);

        // below line is used to get the
        // instance of our FIrebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("Address");

        // initializing our object
        // class variable.
        address = new Address();

        mAddAddressbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String localityname= mlocality.getText().toString();
                String detailadd= mdetailadd.getText().toString();

                if (TextUtils.isEmpty(localityname) && TextUtils.isEmpty(detailadd) ) {
                    // if the text fields are empty
                    // then show the below message.
                    Toast.makeText(RegisterParkingActivity.this, "Please add some data.", Toast.LENGTH_SHORT).show();
                } else {
                    // else call the method to add
                    // data to our database.
                    addDatatoFirebase(localityname, detailadd);

                }


            }
        });

    }

    private void addDatatoFirebase(String name, String detail) {
        // below 3 lines of code is used to set
        // data in our object class.
        address.setLocalityname(name);
        address.setDetailadd(detail);

        // we are use add value event listener method
        // which is called with database reference.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                databaseReference.setValue(address);

                // after adding this data we are showing toast message.
                Toast.makeText(RegisterParkingActivity.this, "data added", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterParkingActivity.this, UserAdminSelection.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(RegisterParkingActivity.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}