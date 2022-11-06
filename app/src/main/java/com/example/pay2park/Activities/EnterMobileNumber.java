package com.example.pay2park.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.pay2park.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class EnterMobileNumber extends AppCompatActivity {

    EditText enternumber;
    Button getotpbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_mobile_number);

        enternumber = findViewById(R.id.input_mobile_number);
        getotpbutton = findViewById(R.id.buttongetotp);

        ProgressBar progressBar= findViewById(R.id.progressbar_sending_otp);
//        getotpbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent= new Intent(EnterMobileNumber.this, OtpVerification.class);
//                startActivity(intent);
//            }
//        });

        getotpbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!enternumber.getText().toString().trim().isEmpty()){
                    if((enternumber.getText().toString().trim()).length() == 10){

                        progressBar.setVisibility(View.VISIBLE);
                        getotpbutton.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + enternumber.getText().toString(),
                                60, TimeUnit.SECONDS,
                                EnterMobileNumber.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                progressBar.setVisibility(View.GONE);
                                getotpbutton.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                progressBar.setVisibility(View.GONE);
                                getotpbutton.setVisibility(View.VISIBLE);
                                Toast.makeText(EnterMobileNumber.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                progressBar.setVisibility(View.GONE);
                                getotpbutton.setVisibility(View.VISIBLE);
                                Intent intent= new Intent(getApplicationContext(), OtpVerification.class);
                                intent.putExtra("mobile", enternumber.getText().toString());
                                intent.putExtra("backendotp", backendotp);
                                startActivity(intent);
                            }
                        });

                        Intent intent= new Intent(getApplicationContext(), OtpVerification.class);
                        intent.putExtra("mobile", enternumber.getText().toString());
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(EnterMobileNumber.this, "Please enter correct number", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(EnterMobileNumber.this, "Mobile number is required for successful registration", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}