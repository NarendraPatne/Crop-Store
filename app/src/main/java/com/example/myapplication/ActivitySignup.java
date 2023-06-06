package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

public class ActivitySignup extends AppCompatActivity {
    EditText ename,emobile,eemail,eaddr,epass;
    FirebaseAuth mAuth;
    String use;
    FirebaseDatabase database;
    DatabaseReference reference;
    Button b;
    String u;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth=FirebaseAuth.getInstance();
        ename=findViewById(R.id.name);
        eemail=findViewById(R.id.email);
        emobile=findViewById(R.id.mobile);
        eaddr=findViewById((R.id.address));
        epass=findViewById(R.id.password);
        b=(Button)findViewById(R.id.btn_signup);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();
            }
        });
    }
    public void Register() {
        database=FirebaseDatabase.getInstance();
        reference=database.getReference("users");
        String name,mobile,email,addr,pass;
        name=String.valueOf(ename.getText().toString());
        mobile=String.valueOf(emobile.getText().toString());
        email=String.valueOf(eemail.getText().toString());
        addr=String.valueOf(eaddr.getText().toString());
        pass=String.valueOf(epass.getText().toString());

        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(mobile) || TextUtils.isEmpty(email)|| TextUtils.isEmpty(addr)|| TextUtils.isEmpty(pass))
        {
            Toast.makeText(ActivitySignup.this,"Please Enter Empty Parameters!",Toast.LENGTH_LONG).show();
        }
        else
        {
            if(pass.length()<6)
            {
                Toast.makeText(ActivitySignup.this, "Minimum Password Length is 6",
                        Toast.LENGTH_SHORT).show();
            }
            else
            {
                mAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(ActivitySignup.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                mAuth.signInWithEmailAndPassword(email,pass);
                                //Toast.makeText(ActivitySignup.this, "Registered Successfully!" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(ActivitySignup.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    mAuth.signInWithEmailAndPassword(email,pass);
                                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                    String userid = firebaseUser.getUid();
                                    HelperClass helperClass = new HelperClass(name, mobile, email, addr);
                                    reference.child(userid).setValue(helperClass);
                                    startActivity(new Intent(ActivitySignup.this, ActivityMain.class));
                                    finish();
                                }
                            }
                        });
        }
//--------------------------------------------------------------------------------------------------------------------------
//                mAuth.createUserWithEmailAndPassword(email, pass)
//                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    u=user.getUid();
//                                } else {
//
//                                    Toast.makeText(ActivitySignup.this, "Authentication failed.",
//                                            Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//                //FirebaseUser u = mAuth.getCurrentUser();
//                HelperClass helperClass = new HelperClass(name, mobile, email, addr);
//                reference.child(u).setValue(helperClass);
//                Toast.makeText(ActivitySignup.this, "You have Registered Successfully!", Toast.LENGTH_SHORT).show();
//                Intent i = new Intent(ActivitySignup.this, ActivityLogin.class);
//                startActivity(i);
            }
        }
    }
//--------------
