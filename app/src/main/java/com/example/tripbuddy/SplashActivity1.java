package com.example.tripbuddy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("CustomSplashScreen")
public class SplashActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_1);

        Button splashNext = findViewById(R.id.getStartedButton);
        splashNext.setOnClickListener(v -> {
            // Chuyển đổi sang SecondActivity
            Intent intent = new Intent(SplashActivity1.this, SplashActivity2.class);
            startActivity(intent);
        });
    }
}
