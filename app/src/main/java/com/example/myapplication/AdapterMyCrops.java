package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class AdapterMyCrops extends RecyclerView.Adapter<AdapterMyCrops.ViewHolder>
{
    ArrayList<Crops>arrayList;
    Context context;
    public AdapterMyCrops(ArrayList<Crops> arrayList, Context context)
    {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(context).inflate(R.layout.mycropslayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMyCrops.ViewHolder holder, int position)
    {
        Crops cr=arrayList.get(position);
        holder.title.setText(cr.name);
        holder.qty1.setText(cr.getQty2());
        holder.price.setText(cr.price3);
        holder.unit.setText(cr.getUnit());
        Glide.with(holder.img.getContext()).load(cr.getCropImg())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark).into(holder.img);
//        holder.delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
//                String sq=user.getUid();
//                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users/"+sq+"/My Cart");
//                ref.child(cr.getCid()).removeValue();
//              // refresh();
//            }
//        });
    }
    @Override
    public int getItemCount()
    {
        return arrayList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,qty1,price,unit;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.name);
            qty1=itemView.findViewById(R.id.qty);
            price=itemView.findViewById(R.id.price);
            img=itemView.findViewById(R.id.cropIcon);
            unit=itemView.findViewById(R.id.unit);
           // delete=itemView.findViewById(R.id.del);
        }
    }
}
