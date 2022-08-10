package com.example.pay2park;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.zip.Inflater;

public class UserAdminSelection extends AppCompatActivity  {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(UserAdminSelection.this, UserAdminSelection.class));
        finish();
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_user_admin_selection);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new UserAdminFragment()).commit();

    }


}