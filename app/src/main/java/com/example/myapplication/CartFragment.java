package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartFragment extends Fragment {
    RecyclerView recyclerViewcart;
    ArrayList<Crops> list;
    DatabaseReference databaseReference;
    CartAdapter adapterc;
    View RootView;
    String naa="",add="",mo="";
    Button ord;
    TextView tot;
    int t=0;
    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (getArguments()!=null)
        {
            naa=getArguments().getString("Name");
            add=getArguments().getString("Add");
            mo=getArguments().getString("Mob");
        }
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewcart=view.findViewById(R.id.recycle_view_cart);
        ord=view.findViewById(R.id.orderbtn);
        tot=view.findViewById(R.id.total);
        recyclerViewcart.setHasFixedSize(true);
        recyclerViewcart.setLayoutManager(new LinearLayoutManager(getActivity()));
        String sa= FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference("users/"+sa+"/My Cart");
        list=new ArrayList<>();
        adapterc=new CartAdapter(list,getActivity(),CartFragment.this);
        recyclerViewcart.setAdapter(adapterc);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap:snapshot.getChildren())
                {
                    Crops c=snap.getValue(Crops.class);
                    list.add(c);
                    t=t+Integer.parseInt(c.getPrice3());
                    tot.setText("Total Amount: Rs "+t);
                }
                adapterc.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
            }
        });

        ord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // getNameAdd();
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snap:snapshot.getChildren())
                        {
                            Crops cr=snap.getValue(Crops.class);
                            String s= FirebaseAuth.getInstance().getCurrentUser().getUid();

                            //-----------------------------My Orders-----------------------------------------------------------------
                            FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                            String sq=user.getUid();
                            String sd=cr.getUid();
                            Deal de= new Deal(sq,cr.getName(),cr.getQty2(),cr.getPrice3(),naa,user.getEmail(),cr.getCropImg(),"Pending",add,cr.cid,mo,sd);
                            DatabaseReference ord=FirebaseDatabase.getInstance().getReference("users/"+sq+"/My Orders");
                            ord.child(cr.getCid()).setValue(de);
                            //-----------------------------My Deals-----------------------------------------------------------------
                            DatabaseReference del=FirebaseDatabase.getInstance().getReference("users/"+sd+"/My Deals");
                            del.child(cr.getCid()).setValue(de);
                            //-----------------------------Clear Cart-----------------------------------------------------------------
                            DatabaseReference ref2=FirebaseDatabase.getInstance().getReference("users/"+s+"/My Cart/"+cr.cid);
                            ref2.removeValue();
                            //----------------------------------------------------------------------------------------------------
                        }
                        adapterc.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "Order Placed!", Toast.LENGTH_SHORT).show();
                        Reload();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    public void Reload()
    {
        getActivity().getSupportFragmentManager().beginTransaction().replace(CartFragment.this.getId(),new CartFragment()).commit();
    }
}