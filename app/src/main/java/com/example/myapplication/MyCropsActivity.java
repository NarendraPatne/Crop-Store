package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyCropsActivity extends AppCompatActivity {
    ImageView back;
    RecyclerView recyclerViewcrops;
    ArrayList<Crops> list;
    DatabaseReference databaseReference;
    AdapterMyCrops adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_crops);
        back=findViewById(R.id.back);
        recyclerViewcrops=findViewById(R.id.recycle_view_mycrop);
        recyclerViewcrops.setHasFixedSize(true);
        recyclerViewcrops.setLayoutManager(new LinearLayoutManager(MyCropsActivity.this));
        String s= FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference("users/"+s+"/My Crops");
        list=new ArrayList<>();
        adapter=new AdapterMyCrops(list,MyCropsActivity.this);
        recyclerViewcrops.setAdapter(adapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap:snapshot.getChildren())
                {
                    Crops c=snap.getValue(Crops.class);
                    list.add(c);
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
                Intent i=new Intent(MyCropsActivity.this,ActivityDashboard.class);
                startActivity(i);
            }
        });
    }
}