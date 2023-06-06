package com.example.myapplication;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import java.util.ArrayList;
//public class Adapter extends FirebaseRecyclerAdapter<Crops,Adapter.MyViewHolder>
//{
//    ArrayList<Crops>arrayList;
//    Context context;
//    public Adapter(@NonNull FirebaseRecyclerOptions options, Context context) {
//        super(options);
//        this.context=context;
//    }
//
//
//    @Override
//    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Crops model)
//    {
//        holder.title.setText(model.getName());
//        holder.qty.setText(model.getQty2());
//        holder.price.setText(model.getPrice3());
////        holder.desc.setText(model.getDesc1());
////        holder.cat.setText(model.getCrops());
//        Glide.with(holder.img.getContext()).load(model.getCropImg())
//                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
//                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark).into(holder.img);
//        holder.img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i=new Intent(context,ProductActivity.class);
//                i.putExtra("title",temp)
//            }
//        });
//    }
//
//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout,parent,false);
//        return new MyViewHolder(view);
//    }
//
//    class MyViewHolder extends RecyclerView.ViewHolder{
//
//        TextView title,qty,price; //desc,cat
//        ImageView img;
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//            title=itemView.findViewById(R.id.name);
//            qty=itemView.findViewById(R.id.qty);
//            price=itemView.findViewById(R.id.price);
////            desc=itemView.findViewById(R.id.desc);
////            cat=itemView.findViewById(R.id.category);
//            img=itemView.findViewById(R.id.cropIcon);
//
//        }
//    }
//}
////--------------------------------------------------------------------------------------------
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>
{
    ArrayList<Crops>arrayList;
    Context context;
    public Adapter(ArrayList<Crops> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        Crops cr=arrayList.get(position);
        holder.title.setText(cr.getName());
        holder.qty.setText(cr.getQty2());
        holder.price.setText(cr.getPrice3());
        holder.unit.setText(cr.getUnit());
        Glide.with(holder.img.getContext()).load(cr.getCropImg())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark).into(holder.img);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context,ProductActivity.class);
                i.putExtra("title",cr.getName());
                i.putExtra("qty",cr.getQty2());
                i.putExtra("price",cr.getPrice3());
                i.putExtra("desc",cr.getDesc1());
                i.putExtra("img",cr.getCropImg());
                i.putExtra("cat",cr.getCrops());
                i.putExtra("cid",cr.getCid());
                i.putExtra("uni",cr.getUnit());
                i.putExtra("uid",cr.getUid());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void filterlist(ArrayList<Crops> filterlist)
    {
        arrayList=filterlist;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,qty,price,unit;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.name);
            qty=itemView.findViewById(R.id.qty);
            price=itemView.findViewById(R.id.price);
            img=itemView.findViewById(R.id.cropIcon);
            unit=itemView.findViewById(R.id.unt);
        }
    }
}
