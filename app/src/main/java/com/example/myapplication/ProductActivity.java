package com.example.myapplication;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProductActivity extends AppCompatActivity
{
    ImageView img;
    TextView name,cat,desc,price,qty,uuit;
    FirebaseDatabase database;
    DatabaseReference reference;
    EditText wqty;
    String imgurl;
    Button add;
    ImageView b;
    String n,c,d,p,q,ci,un,ui,nav,patta;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //---------------------Get From Market Fragment-----------------------------------------------------
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        img=findViewById(R.id.img);
        name=findViewById(R.id.name);
        cat=findViewById(R.id.cate);
        desc=findViewById(R.id.desc);
        price=findViewById(R.id.price);
        qty=findViewById(R.id.qt);
        add=findViewById(R.id.addcart);
        wqty=findViewById(R.id.reqty);
        uuit=findViewById(R.id.unit);
        b=findViewById(R.id.back);
        n=getIntent().getStringExtra("title");
        c=getIntent().getStringExtra("cat");
        d=getIntent().getStringExtra("desc");
        p=getIntent().getStringExtra("price");
        ci=getIntent().getStringExtra("cid");
        un=getIntent().getStringExtra("uni");
        ui=getIntent().getStringExtra("uid");
        q= wqty.getText().toString();
        imgurl=getIntent().getStringExtra("img");
        name.setText("Product Name:  "+n);
        cat.setText("Category:  "+c);
        desc.setText("Description:  "+d);
        price.setText("Price per Unit:  "+p);
        uuit.setText("Unit:  "+un);
        qty.setText("Available Quantity:  "+q);
        Glide.with(ProductActivity.this).load(imgurl)
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark).into(img);
        //---------------------------------------------------Add to Cart Onclick Button-----------------------------------||^
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(ProductActivity.this,ActivityMain.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
            }
        });
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String q=String.valueOf(wqty.getText());
                int pr=Integer.parseInt(p);
                int qt=Integer.parseInt(q);
                p=Integer.toString(pr*qt);
                if(TextUtils.isEmpty(q))
                {
                    wqty.setError("Enter Quantity!");
                    wqty.requestFocus();
                }
                else
                {
                    //------------------------------------------Add To CART--------------------------------------------
                    Fragment fr=new CartFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("Name", nav);
                    bundle.putString("Add",patta);
                    fr.setArguments(bundle);
                    String crop = reference.push().getKey();
                    crop += n;
                    String s= FirebaseAuth.getInstance().getCurrentUser().getUid();
                    FirebaseDatabase fd=FirebaseDatabase.getInstance();
                    DatabaseReference ref=fd.getReference("users/"+s+"/");
                    Crops crops2 = new Crops(n, d, c, q, p,imgurl,ui,ci,un);
                    ref.child("My Cart").child(ci).setValue(crops2);
                    Toast.makeText(ProductActivity.this, "Added to Cart!!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}