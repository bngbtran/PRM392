package com.example.tripbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripbuddy.Adapters.NotificationAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import com.example.tripbuddy.Models.Notification;

public class NotificationActivity extends AppCompatActivity {

    private final List<Notification> recentNotifications = new ArrayList<>();
    private final List<Notification> earlierNotifications = new ArrayList<>();
    private NotificationAdapter notificationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_notification);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        RecyclerView recyclerView = findViewById(R.id.notificationRecyclerView);

        
        notificationAdapter = new NotificationAdapter(recentNotifications);
        recyclerView.setAdapter(notificationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recentNotifications.add(new Notification("Super Offer", "Get 60% off in our first booking", "Sun, 12:40 PM", R.drawable.profile_avatar));
        recentNotifications.add(new Notification("Super Offer", "Get 50% off in our first booking", "Mon, 11:50 PM", R.drawable.profile_avatar));

        
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    
                    notificationAdapter.updateData(recentNotifications);
                } else {
                    
                    notificationAdapter.updateData(earlierNotifications);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                
                notificationAdapter.updateData(recentNotifications);
                notificationAdapter.updateData(earlierNotifications);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                
                notificationAdapter.updateData(recentNotifications);
                notificationAdapter.updateData(earlierNotifications);
            }
        });

        TextView makeAllReadButton = findViewById(R.id.clearAllButton);
        makeAllReadButton.setOnClickListener(v -> {
            
            earlierNotifications.addAll(recentNotifications);
            recentNotifications.clear();
            notificationAdapter.updateData(earlierNotifications);
            notificationAdapter.updateData(recentNotifications);
        });

        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            
            Intent intent = new Intent(NotificationActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}