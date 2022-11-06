package com.example.pay2park.Fragments;

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

import com.example.pay2park.Activities.Addresslist;
import com.example.pay2park.Activities.GetStartedActivity;
import com.example.pay2park.Activities.LogInActivity;
import com.example.pay2park.Activities.ProfileActivity;
import com.example.pay2park.R;
import com.example.pay2park.Activities.RegisterParkingActivity;
import com.example.pay2park.Activities.User_ticket_history;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class UserAdminFragment extends Fragment implements View.OnClickListener {

    public UserAdminFragment(){

    }

    DrawerLayout drawerLayout;
    ImageView navigationBar;
    NavigationView navigationView;
    LinearLayout buyer;
    LinearLayout seller;
    private View view;

    FirebaseAuth mauth;

    private RelativeLayout loginSignup, rent_mypark, avl_park, profile ,user_ticket_history;
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

        mauth=FirebaseAuth.getInstance();
        drawerLayout = (DrawerLayout) view.findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) view.findViewById(R.id.navigationView);

        navigationBar = (ImageView) view.findViewById(R.id.navigationBar);

        loginSignup = (RelativeLayout) view.findViewById(R.id.relativeLayout2);
        rent_mypark = (RelativeLayout) view.findViewById(R.id.relativeLayout3);
        avl_park = (RelativeLayout) view.findViewById(R.id.relativeLayout4);
        profile=(RelativeLayout)view.findViewById(R.id.profile);
        user_ticket_history=(RelativeLayout)view.findViewById(R.id.ticket_history);


//        your_orders = (TextView) view.findViewById(R.id.your_orders);
//        fav_orders = (TextView) view.findViewById(R.id.fav_orders);
//        address_book = (TextView) view.findViewById(R.id.address_book);
//        online_ordering_help = (TextView) view.findViewById(R.id.online_ordering_help);

//        send_feedback = (TextView) view.findViewById(R.id.send_feedback);
//        report_safety = (TextView) view.findViewById(R.id.report_safety);
//        rateus = (TextView) view.findViewById(R.id.rateus);


        navigationBar.setOnClickListener(this);
        loginSignup.setOnClickListener(this);
        rent_mypark.setOnClickListener(this);
        avl_park.setOnClickListener(this);

//        your_orders.setOnClickListener(this);
//        fav_orders.setOnClickListener(this);
//        address_book .setOnClickListener(this);

//        send_feedback.setOnClickListener(this);
//        report_safety.setOnClickListener(this);
//        rateus.setOnClickListener(this);

        profile.setOnClickListener(this);
        user_ticket_history.setOnClickListener(this);

    }
    //RegisterParkingActivity


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.navigationBar:
                drawerLayout.openDrawer(navigationView, true);
                break;

            case R.id.relativeLayout2 :
                Toast.makeText(getContext(), "logged out", Toast.LENGTH_SHORT).show();
                mauth.signOut();
                Intent intent = new Intent(getActivity(), LogInActivity.class);
                startActivity(intent);
                break;
            case R.id.relativeLayout3 :
                //renting
                Intent intent3= new Intent(getActivity(), RegisterParkingActivity.class);
                startActivity(intent3);
                break;

            case R.id.relativeLayout4 :
                //available parking
                Intent intent2= new Intent(getActivity(), Addresslist.class);
                startActivity(intent2);
                break;

            case R.id.profile :
                Toast.makeText(getContext(), "your profile", Toast.LENGTH_SHORT).show();
                Intent intent1= new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent1);
                break;
            case R.id.ticket_history:
                Toast.makeText(getContext(), "Your Ticket History", Toast.LENGTH_SHORT).show();
                Intent intent4=new Intent(getActivity(), User_ticket_history.class);
                startActivity(intent4);


        }
    }
}
