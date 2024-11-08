package com.example.tripbuddy.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripbuddy.Models.Review;
import com.example.tripbuddy.R;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private final ArrayList<Review> reviews; // List of reviews

    // Constructor for the adapter
    public ReviewAdapter(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    // Create a new view holder for each review item
    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item_review layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new ReviewViewHolder(view);
    }

    // Bind the data to the view holder
    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.bind(review);
    }

    // Return the size of the reviews list
    @Override
    public int getItemCount() {
        return reviews.size();
    }

    // ViewHolder class to hold the review item views
    static class ReviewViewHolder extends RecyclerView.ViewHolder {
        private final TextView userNameTextView;
        private final TextView reviewTextView;
        private final TextView timeTextView;
        private final TextView ratingTextView;

        // Constructor for the ViewHolder
        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameTextView = itemView.findViewById(R.id.tvUsername); // ID of user name TextView in item_review layout
            reviewTextView = itemView.findViewById(R.id.tvReviewText); // ID of review TextView in item_review layout
            timeTextView = itemView.findViewById(R.id.tvReviewTime); // ID of time TextView in item_review layout
            ratingTextView = itemView.findViewById(R.id.tvRatingValue); // ID of time TextView in item_review layout
        }

        // Method to bind data to the views
        public void bind(Review review) {
            userNameTextView.setText(review.getUsername());
            reviewTextView.setText(review.getReviewText());
            timeTextView.setText(review.getReviewTime());
            ratingTextView.setText(String.format("%s ", review.getRating()));
        }
    }
}


