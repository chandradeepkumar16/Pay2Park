package com.example.pay2park.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.pay2park.Fragments.UserAdminFragment;
import com.example.pay2park.R;

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