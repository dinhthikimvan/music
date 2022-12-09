package com.example.cuoiky;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainWelcom extends AppCompatActivity {

    Button signup, login;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);

        signup = findViewById(R.id.sinup);
        login = findViewById(R.id.login);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opensignup();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openlogin();
            }
        });
    }

    public void opensignup() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openlogin() {
        Intent intent = new Intent(this, dangnhapActivity.class);
        startActivity(intent);
    }
}