package com.example.pay2park.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pay2park.Models.Addressdata;
import com.example.pay2park.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SlotSelectActivity extends AppCompatActivity {

    ListView retrievedlistViewData;

    String id, price;
    int calcprice;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, dbref_selectedslots;

    List<String> displaylist;
    ArrayAdapter<String> adapter;
    String slots[];
    List<String> mylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot_select);

        retrievedlistViewData= findViewById(R.id.listView_data_retrieved);
        mylist= new ArrayList<>();

        firebaseDatabase= FirebaseDatabase.getInstance();
        displaylist= new ArrayList<>();

        Addressdata addressdata= (Addressdata) getIntent().getSerializableExtra("price");
        Toast.makeText(this, ""+addressdata.getId(), Toast.LENGTH_SHORT).show();
        price= addressdata.getPrice();
        id= addressdata.getId();

        calcprice=Integer.parseInt(price);

        databaseReference=firebaseDatabase.getReference("Parking_address").child(id).child("Slots");
        dbref_selectedslots= firebaseDatabase.getReference("Parking_address").child(id).child("BookedSlots");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    displaylist.clear();
                    for(DataSnapshot ds: snapshot.getChildren())
                    {
                        String list= ds.getValue(String.class);
                        displaylist.add(list);
                    }

                    StringBuilder stringBuilder = new StringBuilder();
                    for(int i=0; i<displaylist.size(); i++){
                        stringBuilder.append(displaylist.get(i)+",");
                    }
                    displayfunction(displaylist);
                    //Toast.makeText(SlotSelectActivity.this, stringBuilder.toString(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void displayfunction(List<String> displaylist) {
        String[] slots= new String[displaylist.size()];
        for(int i=0; i<displaylist.size(); i++){
            slots[i]=displaylist.get(i);
        }
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, slots);
        retrievedlistViewData.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int i=0;
        int id= item.getItemId();
        if(id == R.id.item_done) {
            String itemSelected = "Selected items: \n";
            for (i = 0; i < retrievedlistViewData.getCount(); i++) {

                if (retrievedlistViewData.isItemChecked(i)) {

                    itemSelected = retrievedlistViewData.getItemAtPosition(i) + "\n";
                    mylist.add(itemSelected);
                    dbref_selectedslots.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            dbref_selectedslots.setValue(mylist);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
            startActivity(new Intent(SlotSelectActivity.this, PayNowActivity.class));
            Intent intent = new Intent(SlotSelectActivity.this, PayNowActivity.class);
            intent.putExtra("totaltime", mylist.size());
            intent.putExtra("totalprice", mylist.size()*calcprice);
            intent.putExtra("begintime", mylist.get(0));
            intent.putExtra("endtime", mylist.get(mylist.size()-1));
            intent.putExtra("key", id);
            startActivity(intent);
            Toast.makeText(this, itemSelected, Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}