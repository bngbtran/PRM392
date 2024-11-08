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

    private static final int SPLASH_TIME_OUT = 3000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Check if it's the first launch
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean isFirstLaunch = preferences.getBoolean("isFirstLaunch", true);

        // Delay to transition to the main activity
        if (isFirstLaunch) {
            // First time launch: show splash screen or welcome activity
            setContentView(R.layout.activity_splash);
            preferences.edit().putBoolean("isFirstLaunch", false).apply();
            // Navigate
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(SplashActivity.this, SplashActivity1.class);
                startActivity(intent);
                finish();
            },3000);
            
        } else {
            // Not the first launch: go straight to the main activity
            setContentView(R.layout.activity_splash);
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            },3000);
        }
    }


}