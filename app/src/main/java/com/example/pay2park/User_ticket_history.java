package com.example.pay2park;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

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
    public DatabaseReference dbref_passkey , dbref_soceity_name;
    TextView passkey_tv , society_name_tv;
    String random_passkey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_ticket_history);

        passkey_tv=(TextView)findViewById(R.id.latest_passkey);
        society_name_tv=(TextView)findViewById(R.id.society_name);

        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        dbref_passkey = firebaseDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Passkey").child("passkey");

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

    }
}