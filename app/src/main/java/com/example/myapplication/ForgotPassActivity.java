package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgotPassActivity extends AppCompatActivity {
    Button f;
    EditText email;
    FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        email=findViewById(R.id.email);
        f=findViewById(R.id.btn_forgot);
        mauth=FirebaseAuth.getInstance();
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPass();
            }
        });
    }

    private void resetPass()
    {
        String e=email.getText().toString().trim();
        if(e.isEmpty())
        {
            email.setError("Email is Required");
            email.requestFocus();
            return;
        }
        mauth.sendPasswordResetEmail(e).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(ForgotPassActivity.this, "Check Email Inbox to Reset Password", Toast.LENGTH_LONG).show();
                    Intent i=new Intent(ForgotPassActivity.this,IntroActivity.class);
                }
                else
                {
                    Toast.makeText(ForgotPassActivity.this, "Try Again!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}