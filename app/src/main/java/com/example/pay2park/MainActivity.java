package com.example.pay2park;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private EditText editTextname , editTextemail , editTextpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        mAuth=FirebaseAuth.getInstance();

        TextView logbtn= findViewById(R.id.register);
        Button gettingst= findViewById(R.id.login);

        editTextname =(EditText) findViewById(R.id.signinemail);
        editTextemail=(EditText) findViewById(R.id.signinpass);
        editTextpassword=(EditText) findViewById(R.id.signuppassword);


        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LogInActivity.class));
            }
        });

        gettingst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
//                startActivity(new Intent(MainActivity.this, IntroActivity.class));
//                Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.register:
                startActivity(new Intent(this , LogInActivity.class));
                break;
            case R.id.login:
                Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String email = editTextemail.getText().toString().trim();
        String password = editTextpassword.getText().toString().trim();
        String name = editTextname.getText().toString().trim();

        if(name.isEmpty()){
            editTextname.setError(" User name is required");
            editTextname.requestFocus();
            return;
        }

        if(email.isEmpty()){
            editTextemail.setError(" email is required");
//            Toast.makeText(this, "email is not perfect", Toast.LENGTH_SHORT).show();
            editTextemail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextemail.setError("Please provide valid email");
            editTextemail.requestFocus();
            return;
        }

        if(password.isEmpty() || password.length() <6){
            editTextpassword.setError(" password should have atleast 6 letters");
            editTextpassword.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User( name ,email , password);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        startActivity(new Intent(MainActivity.this, IntroActivity.class));

                                        Toast.makeText(MainActivity.this, "registration done", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(MainActivity.this, "Failed to register", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(MainActivity.this, "Failed to go further", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}