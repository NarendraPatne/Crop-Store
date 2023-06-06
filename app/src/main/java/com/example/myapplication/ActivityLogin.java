package com.example.myapplication;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ActivityLogin extends AppCompatActivity implements View.OnClickListener {
    EditText username, password;
    FirebaseAuth mAuth;
    Button b;
    TextView f;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), ActivityMain.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        f=findViewById(R.id.forgot);
        b = (Button) findViewById(R.id.btn_login);
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityLogin.this,ForgotPassActivity.class));
            }
        });
        b.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
//        Intent i=new Intent(ActivityLogin.this,ActivityMain.class);
//        startActivity(i);
        String mail,pass;
        mail= String.valueOf(username.getText());
        pass= String.valueOf(password.getText());

        if(TextUtils.isEmpty(mail)){
            Toast.makeText(ActivityLogin.this, "Enter email", Toast.LENGTH_SHORT).show();
            username.setError("Enter Valid Email!");
            username.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(ActivityLogin.this, "Enter password", Toast.LENGTH_SHORT).show();
            password.setError("Enter Correct Password!");
            password.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            Toast.makeText(ActivityLogin.this, " Please enter valid email address", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(mail, pass)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplicationContext(),ActivityMain.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(ActivityLogin.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
//    public Boolean validateUsername()
//    {
//     String val=email.getText().toString();
//     if(val.isEmpty())
//     {
//         email.setError("Username Cannot be Empty!");
//         return false;
//     }
//     else
//     {
//         email.setError(null);;
//         return true;
//     }
//    }
//    public Boolean validatePassword()
//    {
//        String val=password.getText().toString();
//        if(val.isEmpty())
//        {
//            password.setError("Password Cannot be Empty!");
//            return false;
//        }
//        else
//        {
//            password.setError(null);;
//            return true;
//        }
//    }
//    public void checkUser()
//    {
//     String  usern=email.getText().toString().trim();
//     String  userp=password.getText().toString().trim();
//     DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users");
//        Query chkuser=reference.orderByChild("email").equalTo(usern);
//        chkuser.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot)
//            {
//                if(snapshot.exists())
//                {
//                    email.setError(null);
//                    String dbpass=snapshot.child(usern).child("pass").getValue(String.class);
//                    if(!Objects.equals(dbpass,userp))
//                    {
//                        email.setError(null);
//                        Intent intent=new Intent(ActivityLogin.this,ActivityMain.class);
//                        startActivity(intent);
//                    }else{
//                        password.setError("Invalid Credentials!");
//                        password.requestFocus();
//                    }
//                }
//            }
//        });
//    }
}