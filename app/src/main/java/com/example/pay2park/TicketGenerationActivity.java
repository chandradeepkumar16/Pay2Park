package com.example.pay2park;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class TicketGenerationActivity extends AppCompatActivity {
    Button downloadticketbtn;
    ImageButton download;
    File file,f;
    LinearLayout linearLayout;
    private Object Activity;
    int count=1;

    String tickett_passkey="";
    passkeyuser passkey;
    TextView username;
    TextView ticket_vhn , ticket_date , ticket_time , ticket_passkey;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference ,dbref_f , dbref_vhn , dbref_ticket_his , dbref_soc_name;
    FirebaseAuth mAuth;
    String id;

    String society_name;

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(TicketGenerationActivity.this, UserAdminSelection.class));
        finish();
    }


    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_generation);


        downloadticketbtn=(Button)findViewById(R.id.downloadticketbtn);
        linearLayout=(LinearLayout)findViewById(R.id.linearl_ticketactivity);

        username=(TextView)findViewById(R.id.username);
        ticket_vhn=(TextView)findViewById(R.id.ticket_vhn);
        ticket_date=(TextView)findViewById(R.id.ticket_date);
        ticket_time=(TextView)findViewById(R.id.ticket_time);
        ticket_passkey=(TextView)findViewById(R.id.ticket_passkey);
        download=(ImageButton)findViewById(R.id.download_ic);
        mAuth=FirebaseAuth.getInstance();

        passkey= new passkeyuser();

        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        ticket_time.setText(currentTime.toString());

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        ticket_date.setText(currentDate.toString());


        Random r = new Random();
        int i1 = r.nextInt(10000 - 1000) + 1000;
        ticket_passkey.setText(String.valueOf(i1));



        Bundle extras1 = getIntent().getExtras();
        if (extras1 != null) {
            id= String.valueOf(extras1.get("key"));

            //The key argument here must match that used in the other activity
        }

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Parking_address").child(id).child("PassKey");
        dbref_ticket_his=firebaseDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Passkey");

        dbref_soc_name=firebaseDatabase.getReference("Parking_address").child(id).child("locality");

        tickett_passkey=(String.valueOf(i1));
        insertpasskeytodatabse(tickett_passkey);
        insertpasskey_currentUser(tickett_passkey);


        dbref_f=firebaseDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Details").child("firstname");
        dbref_vhn=firebaseDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Details").child("vehicleno");






        dbref_soc_name.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                society_name=snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });





        dbref_f.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String st=snapshot.getValue(String.class);
                username.setText(st);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        dbref_vhn.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String st=snapshot.getValue(String.class);
                ticket_vhn.setText(st);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        downloadticketbtn.setOnClickListener(view -> {
            saveimage();

        });
    }

    private void insertpasskeytodatabse(String tickett_passkey) {
        passkey.setPasskey(tickett_passkey.toString());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                databaseReference.setValue(passkey);
//                dbref_ticket_his.setValue(passkey);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    private void insertpasskey_currentUser(String tickett_passkey){
        passkey.setPasskey(tickett_passkey.toString());
//        String ch=society_name+passkey.getPasskey();

        dbref_ticket_his.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                dbref_ticket_his.setValue(passkey);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }



    private void saveimage() {
        linearLayout.setDrawingCacheEnabled(true);
        linearLayout.buildDrawingCache();
        linearLayout.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);

        Bitmap bitmap=linearLayout.getDrawingCache();
        save(bitmap);

    }

    private void save(Bitmap bitmap) {

        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(root +"/Download");

//        String filename ="myimg"+count+".jpg";
        String filename ="myimg.jpg";
        count++;
        File myfile = new File(file , filename);

        if(myfile.exists()){
            myfile.delete();
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(myfile);
            bitmap.compress(Bitmap.CompressFormat.JPEG,50,fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            Toast.makeText(this, "Saved at /Download...", Toast.LENGTH_SHORT).show();
            linearLayout.setDrawingCacheEnabled(false);
        }
        catch (Exception e){
            Toast.makeText(this, "Error"+e.toString(), Toast.LENGTH_SHORT).show();
        }

    }


}
