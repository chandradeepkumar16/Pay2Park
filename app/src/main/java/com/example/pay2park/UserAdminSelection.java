package com.example.pay2park;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class UserAdminSelection extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
//    private Button logoutbtn;
//    private FirebaseAuth mAuth;

    private DrawerLayout drawer;

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
            startActivity(new Intent(UserAdminSelection.this, UserAdminSelection.class));
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_user_admin_selection);

//        logoutbtn =(Button)findViewById(R.id.logout);

        drawer=findViewById(R.id.drawer_layout);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this , drawer ,toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new UsertypeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_userselection);

        }

//        mAuth=FirebaseAuth.getInstance();
//
//        logoutbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mAuth.signOut();
//                startActivity(new Intent(UserAdminSelection.this, LogInActivity.class));
//            }
//        });
//
//        LinearLayout buyer = (LinearLayout) findViewById(R.id.buyerlayout);
//        buyer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(UserAdminSelection.this, UserDetailsActivity.class));
//            }
//        });
//
//
//        LinearLayout seller = (LinearLayout) findViewById(R.id.sellerlayout);
//        seller.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(UserAdminSelection.this, RegisterParkingActivity.class));
//            }
//        });


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Profle_Fragment()).commit();
                break;
            case R.id.nav_setting:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Setting_Fragment()).commit();
                break;
            case R.id.nav_userselection:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UsertypeFragment()).commit();
                break;

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}