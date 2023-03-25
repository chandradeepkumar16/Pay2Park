package com.example.pay2park.Activities;

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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pay2park.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView signup;
    private EditText etemail , etpassword;
    private Button loginbtn;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_log_in);

        mAuth=FirebaseAuth.getInstance();

        signup= (TextView) findViewById(R.id.register);
        signup.setOnClickListener(this);

        loginbtn= (Button) findViewById(R.id.mainlogin);
        loginbtn.setOnClickListener(this);

        etemail=(EditText) findViewById(R.id.login_email);
        etpassword=(EditText) findViewById(R.id.login_pass);
        progressBar=(ProgressBar) findViewById(R.id.prgressbarlogin);
        progressBar.setVisibility(View.INVISIBLE);

        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(LogInActivity.this , UserAdminSelection.class));
        }


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogInActivity.this, MainActivity.class));
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userlogin();
            }
        });


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mainlogin:
                userlogin();
                break;
        }

    }

    private void userlogin() {
        String email = etemail.getText().toString().trim();
        String password = etpassword.getText().toString().trim();


        if(email.isEmpty()){
            etemail.setError(" email is required");
            etemail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etemail.setError("Please provide valid email");
            etemail.requestFocus();
            return;
        }

        if(password.isEmpty() || password.length() <6){
            etpassword.setError(" password should have atleast 6 letters");
            etpassword.requestFocus();
            return;
        }

//        progressBar.setVisibility(View.INVISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.VISIBLE);


                    Toast.makeText(LogInActivity.this, "User logged in", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LogInActivity.this, UserAdminSelection.class));


                }else{
                    progressBar.setVisibility(View.INVISIBLE);

                    Toast.makeText(LogInActivity.this, "User failed to login", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}