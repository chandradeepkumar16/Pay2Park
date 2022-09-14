package com.example.pay2park;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import dagger.BindsInstance;

public class ProfileActivity extends AppCompatActivity {

    private TextView p_num , p_name , p_dlno , p_vhtype , p_lastname , topname ,topemail;
    private Button logout_btn;
    private ImageView walletimg , settingimg;
    FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;
    DatabaseReference dbref_pname , dbref_pnamel ,dbref_p_num , dbref_dlno , dbref_vhtype , dbref_email , dbref_dp , dbref_dpupload;

    ImageView dp;
    Uri imageuri;
    FirebaseStorage storage;
    StorageReference storageReference , mstorage_refereence;
    String dp_hashvalue="";

    parkingiduser parkingid;




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
        dp=(ImageView)findViewById(R.id.profile_pic);

        topname=findViewById(R.id.top_name);
        topemail=findViewById(R.id.top_email);


        firebaseDatabase=FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();

        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();

        parkingid=new parkingiduser();


        dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePic();
            }
        });


        dbref_dp =firebaseDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Booking_id").child("dp_hash");

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



        dbref_dp.child("dp_hashval").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                dp_hashvalue=snapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        mstorage_refereence=FirebaseStorage.getInstance().getReference("images/"+dp_hashvalue+".jpeg");

//        try {
//            final File localfile  = File.createTempFile("tempfile",".jpeg");
//
//            mstorage_refereence.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(ProfileActivity.this, "Picture retrieved", Toast.LENGTH_SHORT).show();
//                    Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
//                    ((ImageView)findViewById(R.id.profile_pic)).setImageBitmap(bitmap);
////                    dp.setImageBitmap(bitmap);
//
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull @NotNull Exception e) {
//
//                    Toast.makeText(ProfileActivity.this, "Failed to fetch dp", Toast.LENGTH_SHORT).show();
//                }
//            });
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
//        }

        final long onemb=1024*1024;
        mstorage_refereence.getBytes(onemb).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {

                Bitmap bm = BitmapFactory.decodeByteArray(bytes ,0, bytes.length);
                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);

                dp.setMinimumHeight(dm.heightPixels);
                dp.setMaxHeight(dm.widthPixels);
                dp.setImageBitmap(bm);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {

                Toast.makeText(ProfileActivity.this, "Failed to fetch dp", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void choosePic() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageuri=data.getData();
            dp.setImageURI(imageuri);
            uploadpicture();
        }
    }

    private void uploadpicture() {

        final ProgressDialog pd= new ProgressDialog(this);
        pd.setTitle("Uploading Image");
        pd.show();


        final String randomkey = UUID.randomUUID().toString();
        StorageReference riveref = storageReference.child("images/"+randomkey+".jpeg");

        riveref.putFile(imageuri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();

                Snackbar.make(findViewById(android.R.id.content), "Image Uploaded" , Snackbar.LENGTH_LONG).show();
                insertdptofirebase(randomkey);


            }
        })

//        storageReference.child("images/"+randomkey).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//
//                dbref_dp.child("dp_hashval").setValue(uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Toast.makeText(ProfileActivity.this, "Image uploaded", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//            }
//        })
//
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "Failed to Upload", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot taskSnapshot) {
                double progresspercent = (100.00*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                pd.setMessage("Progress: " +(int)progresspercent+"%");
            }
        });

    }

    private void insertdptofirebase(String randomkey) {

        parkingid.setDp_hashval(randomkey);
        dbref_dp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dbref_dp.setValue(parkingid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}