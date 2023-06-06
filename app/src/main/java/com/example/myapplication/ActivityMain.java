package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityMain extends AppCompatActivity {
    BottomNavigationView bnv;
    String nav="",patta="",mob="";
//    DrawerLayout drawerLayout;
//    NavigationView navigationView;
//    Toolbar toolbar;
    
    //    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //drawerLayout=findViewById(R.id.drawerLayout);
        //navigationView=findViewById(R.id.navigationDrawer);
        //toolbar=findViewById(R.id.toolBar);
        bnv=findViewById(R.id.Nav);
        String u=FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users").child(u).child("name");
        //DatabaseReference refine=database.getReference("udetails").child(u).child("name");
        //---------------------Retrieve Name-----------------------------
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nav=snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference ref2 = database.getReference("users").child(u).child("addr");
        //DatabaseReference refine=database.getReference("udetails").child(u).child("name");
        //---------------------Retrieve Name-----------------------------
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                patta=snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference ref3 = database.getReference("users").child(u).child("mobile");
        //DatabaseReference refine=database.getReference("udetails").child(u).child("name");
        //---------------------Retrieve Name-----------------------------
        ref3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mob=snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        setSupportActionBar(toolbar);
//        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDraw,R.string.closeDraw);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();
//        if(savedInstanceState==null)
//        {
//            getSupportFragmentManager().beginTransaction().replace(R.id.Container,new HomeFragment()).commit();
//        }
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                int id=item.getItemId();
//                if(id==R.id.homed)
//                {
//                    loadFragment(new HomeFragment());
//                } else if (id==R.id.cropsd) {
//                    Toast.makeText(ActivityMain.this,"My Crops",Toast.LENGTH_SHORT).show();
//                } else if (id==R.id.ordersd) {
//                    Toast.makeText(ActivityMain.this,"My Orders",Toast.LENGTH_SHORT).show();
//                } else if (id==R.id.aboutd) {
//                    Toast.makeText(ActivityMain.this,"About US",Toast.LENGTH_SHORT).show();
//                }else {
//                    //exit
//                    Toast.makeText(ActivityMain.this,"Exit",Toast.LENGTH_SHORT).show();
//                }
//                drawerLayout.closeDrawer(GravityCompat.START);
//                return true;
//            }
//        });
//        --------------------------------------------------------------------------------------------------
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id== R.id.nav_home)
                {
                    loadFrag(new HomeFragment1());
                }else if(id==R.id.nav_add){
                    Intent i=new Intent(ActivityMain.this,AddCropActivity.class);
                    startActivity(i);
                } else if (id==R.id.nav_mar) {
                    loadFrag(new MarketFragment());
                } else if (id==R.id.nav_cart) {
                    loadFrag(new CartFragment());
                }
                return true;
            }
        });
        bnv.setSelectedItemId(R.id.nav_home);
    }
    public void loadFrag(Fragment g)
    {
        Bundle bundle = new Bundle();
        bundle.putString("Name", nav);
        bundle.putString("Add",patta);
        bundle.putString("Mob",mob);
        g.setArguments(bundle);
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.add(R.id.Container,g);
        ft.commit();
    }
}