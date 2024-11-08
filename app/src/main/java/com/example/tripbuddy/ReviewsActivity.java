package com.example.tripbuddy;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripbuddy.Adapters.ReviewAdapter;
import com.example.tripbuddy.Adapters.UserSession;
import com.example.tripbuddy.Models.Review;
import com.example.tripbuddy.ViewModel.ReviewViewModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReviewsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ReviewAdapter reviewAdapter;
    private ReviewViewModel reviewViewModel;
    private ArrayList<Review> reviews; // Assuming you have a Review model
    private int locationId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews); // Your activity layout

        recyclerView = findViewById(R.id.recyclerViewReviews);
        ImageView writeReviewButton = findViewById(R.id.btnWriteReview); // ID of your button to write review

        String destinationName = getIntent().getStringExtra("destinationName");
        locationId = getIntent().getIntExtra("destinationId", -1); // Get the location ID from the intent

        TextView tvDestinationName = findViewById(R.id.tvReviewsTitle);
        tvDestinationName.setText(String.format("Review of %s", destinationName));

        reviews = new ArrayList<>();
        reviewAdapter = new ReviewAdapter(reviews); // Replace with your adapter class
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(reviewAdapter);

        // Initialize the ReviewViewModel
        reviewViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(ReviewViewModel.class);

        // Load reviews from the database
        loadReviews();

        // Set up the Write Review button
        writeReviewButton.setOnClickListener(v -> showReviewDialog());

        // Handle back button press to return to the previous screen
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> onBackPressed());
    }

    private void loadReviews() {
        reviewViewModel.getReviewsForLocation(locationId).observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviewList) {
                reviews.clear(); // Clear the old reviews
                if (reviewList != null) {
                    reviews.addAll(reviewList); // Add new reviews to the list
                }
                else{
                    seedReviews();
                }
                reviewAdapter.notifyDataSetChanged(); // Notify adapter of data change
            }
        });
    }

    public void seedReviews() {
        // Create some default reviews for specific locations
        Review review1 = new Review("Alice", "Amazing experience!", "2024-11-03 10:30:00", 5, 1);
        Review review2 = new Review("Bob", "Had a great time, highly recommend!", "2024-11-03 11:00:00", 5, 1);
        Review review3 = new Review("Charlie", "Not what I expected, but still enjoyable.", "2024-11-03 11:30:00", 5, 2);
        Review review4 = new Review("Diana", "Fantastic views and lovely people.", "2024-11-03 12:00:00", 5, 3);

        new Thread(() -> {
            // Check if the reviews are already seeded for location 1
            if (locationId == 1) {
                reviewViewModel.insert(review1);
                reviewViewModel.insert(review2);
            }
            // Check for location 2
            if (locationId == 2) {
                reviewViewModel.insert(review3);
            }
            // Check for location 3
            if (locationId ==3) {
                reviewViewModel.insert(review4);
            }
        }).start();
    }


    private void showReviewDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LocalDateTime currentDateTime = LocalDateTime.now();
        String currentDate = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String currentTime = currentDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_write_review, null);
        builder.setView(dialogView);

        EditText editTextReview = dialogView.findViewById(R.id.etReviewInput);
        RatingBar ratingBar = dialogView.findViewById(R.id.ratingBar);
        String email = UserSession.getInstance().getUser().getUsername();

        builder.setPositiveButton("Submit", (dialog, which) -> {
            String reviewText = editTextReview.getText().toString().trim();
            double rating = ratingBar.getRating();
            if (!reviewText.isEmpty()) {
                Review newReview = new Review(email, reviewText, currentDate + " " + currentTime, rating, locationId);
                reviewViewModel.insert(newReview); // Use ViewModel to insert review
                reviews.add(newReview);
                reviewAdapter.notifyItemInserted(reviews.size() - 1);
                recyclerView.scrollToPosition(reviews.size() - 1); // Scroll to the new review
                Toast.makeText(this, "Review submitted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please enter a review.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
