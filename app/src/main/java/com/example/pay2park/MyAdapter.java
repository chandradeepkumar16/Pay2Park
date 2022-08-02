package com.example.pay2park;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

   Context context;
   ArrayList<Addressdata> list;

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
      Addressdata addressdata=list.get(position);
      holder.locality.setText(addressdata.getLocality());
      holder.full_address.setText(addressdata.getAddress());
      holder.parkingno.setText(addressdata.getParking());
   }

   @Override
   public int getItemCount() {
      return list.size();
   }

   public static class MyViewHolder extends RecyclerView.ViewHolder{
      TextView locality , full_address, parkingno;
      public MyViewHolder(@NonNull @NotNull View itemView) {
         super(itemView);
         locality=itemView.findViewById(R.id.textlocality);
         full_address=itemView.findViewById(R.id.textaddress);
         parkingno=itemView.findViewById(R.id.textparkingno);
      }
   }
}
