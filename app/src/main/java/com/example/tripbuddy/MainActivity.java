package com.example.tripbuddy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Load the default fragment (HomeFragment)
        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.home);
            // Load the default fragment
            loadFragment(new HomeActivity());
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int homeId = item.getItemId();
            if (homeId == R.id.home) {
                // Handle home action
                selectedFragment = new HomeActivity();
            } else if (homeId == R.id.calendar) {
                // Handle calendar action
                selectedFragment = new CalendarActivity();
            } else if (homeId == R.id.search) {
                // Handle search action
                selectedFragment = new SearchActivity();
            } else if (homeId == R.id.messages) {
                // Handle messages action
                selectedFragment = new QuestionActivity();
                resetFirstLaunch();
            } else if (homeId == R.id.profile) {
                // Handle profile action
                selectedFragment = new ProfileActivity();
                resetFirstLaunch();
            }
            else {
                selectedFragment = new HomeActivity();
            }
            // Load the selected fragment
            loadFragment(selectedFragment);
            return true;
        });
    }

    private void loadFragment(Fragment fragment) {
        // Replace the current fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }

    private void resetFirstLaunch() {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        preferences.edit().putBoolean("isFirstLaunch", true).apply();
        //finishAffinity();
    }
}
