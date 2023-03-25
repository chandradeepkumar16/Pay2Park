package com.example.pay2park.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pay2park.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class User_ticket_history extends AppCompatActivity {

    public FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;
    public DatabaseReference dbref_passkey , dbref_soceity_name, dbref_soceity_id , dbref_occ;
    TextView passkey_tv , society_id_tv , society_name_tv, occupancy_tv;
    String random_passkey , id , soc_name , id_to_name;
    LinearLayout ll;



    Button btn_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_ticket_history);

        passkey_tv=(TextView)findViewById(R.id.latest_passkey);
        society_id_tv=(TextView)findViewById(R.id.society_id);
        society_name_tv=(TextView)findViewById(R.id.society_namefull);
        btn_view=(Button)findViewById(R.id.btn);
        occupancy_tv=(TextView)findViewById(R.id.occupancy);
        ll=(LinearLayout)findViewById(R.id.ll_occ);

        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        dbref_passkey = firebaseDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Passkey").child("passkey");
        dbref_soceity_id = firebaseDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Booking_id").child("id");




        dbref_soceity_id.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                id=snapshot.getValue(String.class);
                society_id_tv.setText(id);

//                ProgressDialog dialog = ProgressDialog.show(User_ticket_history.this, "", "Fetching id...",
//                        true);
//                dialog.show();
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    public void run() {
//                        dialog.dismiss();
//
//                    }
//                }, 3000);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });



        dbref_passkey.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                random_passkey=snapshot.getValue(String.class);
                passkey_tv.setText(random_passkey);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display();
            }
        });

    }

    private void display() {


        ProgressDialog dialog = ProgressDialog.show(User_ticket_history.this, "", "Setting id...",
                true);
        dialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                dialog.dismiss();

            }
        }, 1000);

        id_to_name=society_id_tv.getText().toString();

        dbref_occ = firebaseDatabase.getReference("Parking_address").child(id_to_name).child("Occupancy");
        dbref_occ.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                int oc = snapshot.getValue(Integer.class);
                if(oc==0){
                    occupancy_tv.setText(" Your car is not in the parking ");
                }
                else{
                    occupancy_tv.setText(" Your car is parked safely ");
                }

                Toast.makeText(User_ticket_history.this, ""+oc ,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        dbref_soceity_name = firebaseDatabase.getReference("Parking_address").child(id_to_name).child("locality");
        dbref_soceity_name.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                soc_name=snapshot.getValue(String.class);
                ProgressDialog dialog = ProgressDialog.show(User_ticket_history.this, "", "Society name fetching...",
                        true);
                dialog.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        dialog.dismiss();
                        society_name_tv.setText(soc_name);
                    }
                }, 2000);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });



    }
}