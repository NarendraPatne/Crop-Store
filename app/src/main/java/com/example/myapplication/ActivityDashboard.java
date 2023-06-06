package com.example.myapplication;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityDashboard extends AppCompatActivity implements View.OnClickListener
{
    TextView home,crops,orders,about,logout,deal,n,e;
    FirebaseUser user;
    FirebaseAuth mAuth;
    FirebaseDatabase fd;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        home=findViewById(R.id.home11);
        crops=findViewById(R.id.crops);
        orders=findViewById(R.id.orders);
        about=findViewById(R.id.about);
        logout=findViewById(R.id.logout);
        deal=findViewById(R.id.deals);
        n=findViewById(R.id.name);
        e=findViewById(R.id.email);
        String u=FirebaseAuth.getInstance().getCurrentUser().getUid();
        fd=FirebaseDatabase.getInstance();
        ref=fd.getReference("users").child(u).child("name");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String na=snapshot.getValue(String.class);
                n.setText(na);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mAuth=FirebaseAuth.getInstance();
        user=FirebaseAuth.getInstance().getCurrentUser();
//        n.setText(user.getDisplayName());
        e.setText(user.getEmail());
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ActivityDashboard.this,ActivityMain.class);
                startActivity(i);
            }
        });
        crops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ActivityDashboard.this,MyCropsActivity.class);
                startActivity(i);
            }
        });
        deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(ActivityDashboard.this,MyDealsActivity.class);
                startActivity(i);
            }
        });
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ActivityDashboard.this,MyOrdersActivity.class);
                startActivity(i);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ActivityDashboard.this,AboutActivity.class);
                startActivity(i);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent i=new Intent(ActivityDashboard.this,IntroActivity.class);
                startActivity(i);
                finish();
                Toast.makeText(ActivityDashboard.this, "Log Out Successful!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onClick(View view) {

    }
}