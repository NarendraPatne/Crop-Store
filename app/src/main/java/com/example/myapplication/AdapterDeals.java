package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterDeals extends RecyclerView.Adapter<AdapterDeals.ViewHolder>
{
    ArrayList<Deal>arrayList;
    Context context;
    AdapterDeals adapterDeals;
    public AdapterDeals(ArrayList<Deal> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        adapterDeals=this;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.mydealslayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDeals.ViewHolder holder, int position) {
        Deal cr=arrayList.get(position);
        if(cr.getStatus().equals("Pending"))
        {
            holder.deliver.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.deliver.setVisibility(View.INVISIBLE);
        }
        holder.title.setText(cr.getName());
        holder.qty.setText(cr.getQty());
        holder.price.setText(cr.getPrice());
        holder.cname.setText(cr.getCname());
        holder.email.setText(cr.getCemail());
        holder.stat.setText(cr.getStatus());
        holder.adr.setText(cr.getAdd());
        holder.moon.setText(cr.getMno());
        Glide.with(holder.img.getContext()).load(cr.img)
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark).into(holder.img);
        holder.deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                String sq=user.getUid();
                DatabaseReference ord= FirebaseDatabase.getInstance().getReference("users/"+sq+"/My Deals");
                ord.child(cr.getCrpid()).child("status").setValue("Sent for Delivery!");
                DatabaseReference orc= FirebaseDatabase.getInstance().getReference("users/"+cr.getCid()+"/My Orders");
                orc.child(cr.getCrpid()).child("status").setValue("Sent for Delivery!");
                holder.deliver.setVisibility(View.INVISIBLE);
                refresh();
            }
        });
    }

    private void refresh()
    {
        Intent i=new Intent(context,MyDealsActivity.class);
        context.startActivity(i);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,qty,price,cname,email,stat,adr,moon;
        ImageView img;
        Button deliver;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.namec);
            qty=itemView.findViewById(R.id.qty);
            price=itemView.findViewById(R.id.price);
            img=itemView.findViewById(R.id.cropIcon);
            cname=itemView.findViewById(R.id.cname);
            email=itemView.findViewById(R.id.em);
            deliver=itemView.findViewById(R.id.delibtn);
            stat=itemView.findViewById(R.id.status);
            adr=itemView.findViewById(R.id.addp);
            moon=itemView.findViewById(R.id.mono);
        }
    }
}
