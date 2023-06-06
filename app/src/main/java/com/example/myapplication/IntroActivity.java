package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class IntroActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button button, button2;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        button = (Button) findViewById(R.id.button);
        mAuth=FirebaseAuth.getInstance();
        button2 = (Button) findViewById(R.id.button2);
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
    }
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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.button:
            {
                Toast.makeText(this,"LogIn",Toast.LENGTH_LONG).show();
                Intent i=new Intent(IntroActivity.this,ActivityLogin.class);
                startActivity(i);
                break;
            }
            case R.id.button2:
            {
                Toast.makeText(this,"SignUp",Toast.LENGTH_LONG).show();
                Intent i=new Intent(IntroActivity.this,ActivitySignup.class);
                startActivity(i);
                break;
            }
        }
    }
}