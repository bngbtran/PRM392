package com.example.tripbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tripbuddy.Adapters.HistoryAdapter;
import com.example.tripbuddy.Adapters.UserSession;
import com.example.tripbuddy.Models.History;
import com.example.tripbuddy.ViewModel.DestinationViewModel;
import com.example.tripbuddy.ViewModel.HistoryViewModel;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private HistoryAdapter historyAdapter;
    private HistoryViewModel historyViewModel;
    private DestinationViewModel destinationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Initialize RecyclerView and Adapter
        RecyclerView recyclerViewHistory = findViewById(R.id.recyclerViewHistory);
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(this));

        // Initialize ViewModels
        destinationViewModel = new ViewModelProvider(this).get(DestinationViewModel.class);
        historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);

        // Initialize Adapter with DestinationViewModel and LifecycleOwner
        historyAdapter = new HistoryAdapter(destinationViewModel, this);
        recyclerViewHistory.setAdapter(historyAdapter);

        // Set up navigation buttons
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(this::navigateToMainActivity);

        TextView cancelButton = findViewById(R.id.textCancel);
        cancelButton.setOnClickListener(this::navigateToMainActivity);

        // Observe History Data
        observeHistoryData();
    }

    private void observeHistoryData() {
        historyViewModel.getHistoryByUserId(UserSession.getInstance().getUser().getUserId())
                .observe(this, this::updateHistoryList);
    }

    private void updateHistoryList(List<History> historyList) {
        historyAdapter.setHistoryList(historyList);
    }

    private void navigateToMainActivity(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}
