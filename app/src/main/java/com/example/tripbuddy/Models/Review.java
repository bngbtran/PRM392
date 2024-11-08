package com.example.tripbuddy.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Review")
public class Review {
    @PrimaryKey(autoGenerate = true)
    private int reviewId;
    private String username;
    private String reviewText;
    private String reviewTime;
    private int locationId;
    private double rating; // New field to associate with a location

    public Review(String username, String reviewText, String reviewTime, double rating, int locationId) {
        this.username = username;
        this.reviewText = reviewText;
        this.reviewTime = reviewTime;
        this.rating = rating;
        this.locationId = locationId;
    }

    // Getters and setters
    public int getReviewId() { return reviewId; }
    public void setReviewId(int reviewId) { this.reviewId = reviewId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getReviewText() { return reviewText; }
    public void setReviewText(String reviewText) { this.reviewText = reviewText; }
    public String getReviewTime() { return reviewTime; }
    public void setReviewTime(String reviewTime) { this.reviewTime = reviewTime; }
    public int getLocationId() { return locationId; }
    public void setLocationId(int locationId) { this.locationId = locationId; }
    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
}
