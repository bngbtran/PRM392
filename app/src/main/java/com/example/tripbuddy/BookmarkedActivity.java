package com.example.tripbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripbuddy.Adapters.DestinationAdapter;
import com.example.tripbuddy.Adapters.UserSession;
import com.example.tripbuddy.Models.BookMarkDestination;
import com.example.tripbuddy.Models.Destination;
import com.example.tripbuddy.ViewModel.BookMarkViewModel;
import com.example.tripbuddy.ViewModel.DestinationViewModel;

import java.util.ArrayList;
import java.util.List;

public class BookmarkedActivity extends AppCompatActivity {

    private List<Destination> destinationList = new ArrayList<>();
    private RecyclerView destinationRecyclerView;
    private DestinationAdapter destinationAdapter;
    private DestinationViewModel destinationViewModel;
    private BookMarkViewModel bookMarkViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarked);

        // Initialize RecyclerView
        destinationRecyclerView = findViewById(R.id.destinationRecyclerView);
        destinationRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Initialize the adapter and set it to the RecyclerView
        destinationAdapter = new DestinationAdapter(destinationList, this);
        destinationRecyclerView.setAdapter(destinationAdapter);

        // Initialize DestinationViewModel
        destinationViewModel = new ViewModelProvider(this).get(DestinationViewModel.class);

        // Load bookmarked destinations
        loadBookmarkedDestinations();

        // Set up back button
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(this::onBackButtonClick);
    }

    private void loadBookmarkedDestinations() {
        int userId = UserSession.getInstance().getUser().getUserId();
        BookMarkViewModel bookMarkViewModel = new ViewModelProvider(this).get(BookMarkViewModel.class);
        // Observe the list of bookmarked destinations for the user
        bookMarkViewModel.getUserBookmarks(userId).observe(this, bookmarkedDestinations -> {
            if (bookmarkedDestinations != null && !bookmarkedDestinations.isEmpty()) {
                for (BookMarkDestination bookmark : bookmarkedDestinations) {
                    int destinationId = bookmark.getDestinationId();

                    // Fetch and observe each destination's details
                    destinationViewModel.getDestinationById(destinationId).observe(this, destination -> {
                        if (destination != null) {
                            destinationList.add(destination);
                            destinationAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(BookmarkedActivity.this, "Destination not found", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                Toast.makeText(this, "No bookmarked destinations found", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void onBackButtonClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
