package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdapterMyOrders extends RecyclerView.Adapter<AdapterMyOrders.ViewHolder>
{
    ArrayList<Deal>arrayList;
    Context context;
    public AdapterMyOrders(ArrayList<Deal> arrayList, Context context)
    {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(context).inflate(R.layout.myorderlayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMyOrders.ViewHolder holder, int position)
    {
        Deal cr=arrayList.get(position);
        if (cr.getStatus().equals("Pending"))
        {
            holder.can.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.can.setVisibility(View.INVISIBLE);
        }
        holder.title.setText(cr.name);
        holder.qty1.setText(cr.qty);
        holder.price.setText(cr.price);
        holder.stat.setText(cr.status);
        Glide.with(holder.img.getContext()).load(cr.img)
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark).into(holder.img);
        holder.can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                String sq=user.getUid();
//                DatabaseReference ord= FirebaseDatabase.getInstance().getReference("users/"+sq+"/My Orders");
//                ord.child(cr.getCrpid()).child("status").setValue("Sent for Delivery!");
                //----------------------------------------------------
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users/"+sq+"/My Orders");
                ref.child(cr.getCrpid()).removeValue();
                DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("users/"+cr.getFid()+"/My Deals");
                ref2.child(cr.getCrpid()).removeValue();
                Toast.makeText(context, "Order Cancelled!", Toast.LENGTH_SHORT).show();
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
    public int getItemCount()
    {
        return arrayList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,qty1,price,stat;
        ImageView img;
        Button can;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.name);
            qty1=itemView.findViewById(R.id.qty);
            price=itemView.findViewById(R.id.price);
            img=itemView.findViewById(R.id.cropIcon);
            stat=itemView.findViewById(R.id.status);
            can=itemView.findViewById(R.id.cancel);
        }
    }
}
