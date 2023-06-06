package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyDealsActivity extends AppCompatActivity {
    ImageView back;
    RecyclerView recyclerVieworder;
    ArrayList<Deal> list;
    DatabaseReference databaseReference;
    AdapterDeals adapter;
    TextView tot;
    int t=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_deals);
        back=findViewById(R.id.back);
        tot=findViewById(R.id.total);
        recyclerVieworder=findViewById(R.id.recycle_view_deal);
        recyclerVieworder.setHasFixedSize(true);
        recyclerVieworder.setLayoutManager(new LinearLayoutManager(MyDealsActivity.this));
        String s= FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference("users/"+s+"/My Deals");
        list=new ArrayList<>();
        adapter=new AdapterDeals(list,MyDealsActivity.this);
        recyclerVieworder.setAdapter(adapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap:snapshot.getChildren())
                {
                    Deal c=snap.getValue(Deal.class);
                    list.add(c);
                    t+=Integer.parseInt(c.getPrice());
                    tot.setText("Total Deals Amount: Rs "+t);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MyDealsActivity.this,ActivityDashboard.class);
                startActivity(i);
            }
        });
    }
}