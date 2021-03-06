package com.example.pay2park;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class UserDetailsActivity extends AppCompatActivity {


//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        startActivity(new Intent(UserDetailsActivity.this, UserAdminSelection.class));
//        finish();
//    }

    private EditText buyerfirstname;
    private EditText buyerlastname;
    private EditText buyercontact;
    private EditText buyervehicleno;
    private EditText buyervehicletype;
    private Button detailupload;


    // creating a variable for our
    // Firebase Database.
    FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;

    // creating a variable for
    // our object class
    DetailsModel detailsModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_user_details);

        buyerfirstname=(EditText)findViewById(R.id.editTextTextPersonName6);
        buyerlastname=(EditText) findViewById(R.id.editTextTextPersonName7);
        buyercontact=(EditText)findViewById(R.id.editTextPhone);
        buyervehicleno=(EditText)findViewById(R.id.vehicleno);
        buyervehicletype=(EditText)findViewById(R.id.vehicletype);
        detailupload=(Button) findViewById(R.id.donedetails);

        firebaseDatabase= FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();


        databaseReference=firebaseDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Details");

        detailsModel= new DetailsModel();

        detailupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });

    }

    private void upload() {
        String fname= buyerfirstname.getText().toString();
        String lname= buyerlastname.getText().toString();
        String contact= buyercontact.getText().toString();
        String vehicleno= buyervehicleno.getText().toString();
        String vehicletype= buyervehicletype.getText().toString();

        if(TextUtils.isEmpty(fname) && TextUtils.isEmpty(lname) && TextUtils.isEmpty(contact) && TextUtils.isEmpty(vehicleno) && TextUtils.isEmpty(vehicletype)){
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }else{
            addDatatoFirebase(fname, lname, contact, vehicleno, vehicletype);
        }

    }

    private void addDatatoFirebase(String fname, String lname, String contact, String vehicleno, String vehicletype) {
        detailsModel.setFirstname(fname);
        detailsModel.setLastname(lname);
        detailsModel.setContact(contact);
        detailsModel.setVehicleno(vehicleno);
        detailsModel.setVehicletype(vehicletype);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.setValue(detailsModel);
                Toast.makeText(UserDetailsActivity.this, "Data added", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UserDetailsActivity.this, Addresslist.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserDetailsActivity.this, "Failed to add data, try again", Toast.LENGTH_SHORT).show();
            }
        });
    }
}