package com.example.pay2park;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

public class UserAdminFragment extends Fragment implements View.OnClickListener {

    public UserAdminFragment(){

    }

    DrawerLayout drawerLayout;
    ImageView navigationBar;
    NavigationView navigationView;
    LinearLayout buyer;
    LinearLayout seller;
    private View view;
    private RelativeLayout loginSignup, bookmarks, gold, profile;
    private TextView your_orders, fav_orders, address_book, online_ordering_help, send_feedback, report_safety, rateus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_useradminselection, container, false);

        onSetNavigationDrawerEvents();
        return view;

    }

    private void onSetNavigationDrawerEvents() {
        buyer=(LinearLayout) view.findViewById(R.id.buyerlayout);
        buyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), GetStartedActivity.class));
            }
        });
        seller=(LinearLayout) view.findViewById(R.id.sellerlayout);
        seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RegisterParkingActivity.class));
            }
        });
        drawerLayout = (DrawerLayout) view.findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) view.findViewById(R.id.navigationView);

        navigationBar = (ImageView) view.findViewById(R.id.navigationBar);

        loginSignup = (RelativeLayout) view.findViewById(R.id.relativeLayout2);
        bookmarks = (RelativeLayout) view.findViewById(R.id.relativeLayout3);
        gold = (RelativeLayout) view.findViewById(R.id.relativeLayout4);
        profile=(RelativeLayout)view.findViewById(R.id.profile);

        your_orders = (TextView) view.findViewById(R.id.your_orders);
        fav_orders = (TextView) view.findViewById(R.id.fav_orders);
        address_book = (TextView) view.findViewById(R.id.address_book);
        online_ordering_help = (TextView) view.findViewById(R.id.online_ordering_help);
        send_feedback = (TextView) view.findViewById(R.id.send_feedback);
        report_safety = (TextView) view.findViewById(R.id.report_safety);
        rateus = (TextView) view.findViewById(R.id.rateus);

        navigationBar.setOnClickListener(this);
        loginSignup.setOnClickListener(this);
        bookmarks.setOnClickListener(this);
        gold.setOnClickListener(this);
        your_orders.setOnClickListener(this);
        fav_orders.setOnClickListener(this);
        address_book .setOnClickListener(this);
        send_feedback.setOnClickListener(this);
        report_safety.setOnClickListener(this);
        rateus.setOnClickListener(this);
        profile.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.navigationBar:
                drawerLayout.openDrawer(navigationView, true);
                break;
            case R.id.your_orders:
                Toast.makeText(getContext(), "your_orders", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fav_orders:
                Toast.makeText(getContext(), "fav_orders", Toast.LENGTH_SHORT).show();
                break;
            case R.id.address_book:
                Toast.makeText(getContext(), "address_book", Toast.LENGTH_SHORT).show();
                break;
            case R.id.online_ordering_help:
                Toast.makeText(getContext(), "online_ordering_help", Toast.LENGTH_SHORT).show();
                break;
            case R.id.send_feedback:
                Toast.makeText(getContext(), "send_feedback", Toast.LENGTH_SHORT).show();
                break;
            case R.id.report_safety:
                Toast.makeText(getContext(), "report_safety", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rateus :
                Toast.makeText(getContext(), "rateus", Toast.LENGTH_SHORT).show();
                break;
            case R.id.relativeLayout2 :
                Toast.makeText(getContext(), "loginsignup", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), LogInActivity.class);
                startActivity(intent);
                break;
            case R.id.relativeLayout3 :
                Toast.makeText(getContext(), "bookmark", Toast.LENGTH_SHORT).show();
                break;
            case R.id.relativeLayout4 :
                Toast.makeText(getContext(), "gold", Toast.LENGTH_SHORT).show();
                break;
            case R.id.profile :
                Toast.makeText(getContext(), "your profile", Toast.LENGTH_SHORT).show();
                Intent intent1= new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent1);
                break;

        }
    }
}
