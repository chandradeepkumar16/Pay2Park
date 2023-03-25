package com.example.pay2park.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pay2park.Models.DetailsModel;
import com.example.pay2park.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

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
//    private EditText buyervehicletype;
    private Button detailupload;
    private EditText buyeruploaddl;

//    private Spinner spinner;
    private EditText buyervehicletype;

    String[] vehicle ={"Car" , "Bike" , "Scooty" , "Truck"};


    String value="";
    // creating a variable for our
    // Firebase Database.
    FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference , dbref_f , dbref_l , dbref_contact , dbref_dl , dbref_vhn , dbref_type;;

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

        buyervehicletype=(EditText) findViewById(R.id.vehicletype);

        detailupload=(Button) findViewById(R.id.donedetails);
        buyeruploaddl = (EditText) findViewById(R.id.uploaddl);


        firebaseDatabase= FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();

//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(UserDetailsActivity.this , android.R.layout.simple_spinner_item, vehicle);
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(arrayAdapter);
//
//        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                value=adapterView.getItemAtPosition(position).toString();
//
//            }
//        });




        databaseReference=firebaseDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Details");
        dbref_f=firebaseDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Details").child("firstname");
        dbref_l=firebaseDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Details").child("lastname");
        dbref_contact=firebaseDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Details").child("contact");
        dbref_dl=firebaseDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Details").child("uploaddl");
        dbref_vhn=firebaseDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Details").child("vehicleno");
        dbref_type=firebaseDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Details").child("vehicletype");

        detailsModel= new DetailsModel();

        detailupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });


        ProgressDialog dialog = ProgressDialog.show(this, "", "Your Details...",
                true);

        dialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                dialog.dismiss();
            }
        }, 3000);

        dbref_f.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String st = snapshot.getValue(String.class);
                buyerfirstname.setText(st);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        dbref_l.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String st = snapshot.getValue(String.class);
                buyerlastname.setText(st);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        dbref_contact.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String st = snapshot.getValue(String.class);
                buyercontact.setText(st);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        dbref_dl.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String st = snapshot.getValue(String.class);
                buyeruploaddl.setText(st);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        dbref_vhn.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String st = snapshot.getValue(String.class);
                buyervehicleno.setText(st);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        dbref_type.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String st = snapshot.getValue(String.class);
                buyervehicletype.setText(st);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }




    private void upload() {
        String fname= buyerfirstname.getText().toString();
        String lname= buyerlastname.getText().toString();
        String contact= buyercontact.getText().toString();
        String vehicleno= buyervehicleno.getText().toString();
        String vehicletype= buyervehicletype.getText().toString();
        String dlnumber = buyeruploaddl.getText().toString();

        if(TextUtils.isEmpty(fname) && TextUtils.isEmpty(lname) && TextUtils.isEmpty(contact) && TextUtils.isEmpty(vehicleno) && TextUtils.isEmpty(vehicletype)){
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }else{
            addDatatoFirebase(fname, lname, contact, vehicleno, vehicletype , dlnumber);
        }

    }

    private void addDatatoFirebase(String fname, String lname, String contact, String vehicleno, String vehicletype , String dlnumber) {
        detailsModel.setFirstname(fname);
        detailsModel.setLastname(lname);
        detailsModel.setContact(contact);
        detailsModel.setVehicleno(vehicleno);
        detailsModel.setVehicletype(vehicletype);
        detailsModel.setUploaddl(dlnumber);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.setValue(detailsModel);
//                Toast.makeText(UserDetailsActivity.this, "Data added", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UserDetailsActivity.this, Addresslist.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(UserDetailsActivity.this, "Failed to add data, try again", Toast.LENGTH_SHORT).show();
            }
        });
    }
}