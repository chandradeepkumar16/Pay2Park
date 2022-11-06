package com.example.pay2park.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pay2park.Activities.DateSetActivity;
import com.example.pay2park.Models.Addressdata;
import com.example.pay2park.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

   Context context;
   ArrayList<Addressdata> list;
   TextView Parkingdate;

   FirebaseDatabase firebaseDatabase;
   FirebaseAuth mAuth;
   DatabaseReference dbref_st;

   public MyAdapter(Context context, ArrayList<Addressdata> list) {
      this.context = context;
      this.list = list;
   }

   @NonNull
   @NotNull
   @Override
   public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
      View v = LayoutInflater.from(context).inflate(R.layout.address_entry,parent,false);
      return new MyViewHolder(v);
   }

   @Override
   public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
      firebaseDatabase= FirebaseDatabase.getInstance();
      mAuth=FirebaseAuth.getInstance();

      Addressdata addressdata=list.get(position);
      holder.locality.setText(addressdata.getLocality());
      holder.full_address.setText(addressdata.getAddress());
      holder.parkingno.setText(addressdata.getParking());
      holder.price.setText(addressdata.getPrice());

      holder.fulldetail_add.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            Toast.makeText(context, addressdata.getId(), Toast.LENGTH_SHORT).show();
            Intent intent= new Intent(context, DateSetActivity.class);
            intent.putExtra("price", addressdata);
            intent.putExtra("id", addressdata);
            context.startActivity(intent);
         }
      });

      int[][] states = new int[][] {
              new int[] { android.R.attr.state_enabled}, // enabled
              new int[] {-android.R.attr.state_enabled}, // disabled
              new int[] {-android.R.attr.state_checked}, // unchecked
              new int[] { android.R.attr.state_pressed}  // pressed
      };

      int[] colors = new int[] {
              Color.RED,
              Color.BLACK,
              Color.GREEN,
              Color.BLUE
      };

      ColorStateList myList = new ColorStateList(states, colors);



      dbref_st= firebaseDatabase.getReference("Parking_address").child(addressdata.getId()).child("Status");

      dbref_st.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//            String data = snapshot.getValue(Boolean.parseBoolean("status")).toString();
            String ch = snapshot.child("status").getValue(String.class);

            if(ch.equals("booked")){
               holder.radioButton_status.setButtonTintList(myList);
            }


         }

         @Override
         public void onCancelled(@NonNull @NotNull DatabaseError error) {

         }
      });


   }

   @Override
   public int getItemCount() {
      return list.size();
   }

   public static class MyViewHolder extends RecyclerView.ViewHolder{
      TextView locality , full_address, parkingno, price;
      LinearLayout fulldetail_add;
      RadioButton radioButton_status;
      public MyViewHolder(@NonNull @NotNull View itemView) {
         super(itemView);
         locality=itemView.findViewById(R.id.textlocality);
         full_address=itemView.findViewById(R.id.textaddress);
         parkingno=itemView.findViewById(R.id.textparkingno);
         price=itemView.findViewById(R.id.textpricing);
         fulldetail_add=itemView.findViewById(R.id.fulldetails_address);
         radioButton_status=itemView.findViewById(R.id.status_radiobtn);
      }
   }
}
