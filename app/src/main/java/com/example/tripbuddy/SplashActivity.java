package com.example.tripbuddy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 3000; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean isFirstLaunch = preferences.getBoolean("isFirstLaunch", true);

        
        if (isFirstLaunch) {
            
            setContentView(R.layout.activity_splash);
            preferences.edit().putBoolean("isFirstLaunch", false).apply();
            
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(SplashActivity.this, SplashActivity1.class);
                startActivity(intent);
                finish();
            }, 3000);

        } else {
            
            setContentView(R.layout.activity_splash);
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }, 3000);
        }
    }


}