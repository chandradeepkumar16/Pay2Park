package com.example.pay2park;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;

public class UserAdminSelection extends AppCompatActivity {
    private Button logoutbtn;
    private FirebaseAuth mAuth;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(UserAdminSelection.this, UserAdminSelection.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_user_admin_selection);
        logoutbtn =(Button)findViewById(R.id.logout);

        mAuth=FirebaseAuth.getInstance();

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(UserAdminSelection.this, LogInActivity.class));
            }
        });

        LinearLayout buyer = (LinearLayout) findViewById(R.id.buyerlayout);
        buyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserAdminSelection.this, UserDetailsActivity.class));
            }
        });

        LinearLayout seller = (LinearLayout) findViewById(R.id.sellerlayout);
        seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserAdminSelection.this, RegisterParkingActivity.class));
            }
        });
    }


}