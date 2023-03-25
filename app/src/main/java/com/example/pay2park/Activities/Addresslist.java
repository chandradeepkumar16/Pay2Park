package com.example.pay2park.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SearchView;

import com.example.pay2park.Adapters.MyAdapter;
import com.example.pay2park.Models.Addressdata;
import com.example.pay2park.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Addresslist extends AppCompatActivity {


    RecyclerView recyclerView;
    ArrayList<Addressdata> list;
    DatabaseReference databaseReference;


    MyAdapter adapter;
    SearchView searchview;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Addresslist.this, UserAdminSelection.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_addresslist);

        recyclerView=findViewById(R.id.recyclerview);
        databaseReference= FirebaseDatabase.getInstance().getReference("Parking_address");
        searchview= findViewById(R.id.searchview);

//        Parkingdate=findViewById(R.id.parkingdate);
//        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
//        Parkingdate.setText(currentDate.toString());

        list= new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new MyAdapter(this ,list);
        recyclerView.setAdapter(adapter);




        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Addressdata addressdata = dataSnapshot.getValue(Addressdata.class);
                    list.add(addressdata);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        if(searchview!=null){
            searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    search(s);
                    return true;
                }
            });
        }

    }

    private void search(String str) {
        ArrayList<Addressdata> mylist= new ArrayList<>();
        for(Addressdata object: list){
            if(object.getLocality().toLowerCase().contains(str.toLowerCase())){
                mylist.add(object);
            }
        }
        adapter=new MyAdapter(this, mylist);
        recyclerView.setAdapter(adapter);

    }

}