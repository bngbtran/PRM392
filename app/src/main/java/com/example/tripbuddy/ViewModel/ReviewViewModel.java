package com.example.tripbuddy.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tripbuddy.Models.Review;
import com.example.tripbuddy.Repository.ReviewRepository;

import java.util.List;

public class ReviewViewModel extends AndroidViewModel{
    private final ReviewRepository reviewRepository;
    private MutableLiveData<Double> averageRating = new MutableLiveData<>();
    private MutableLiveData<Integer> numberOfRatings = new MutableLiveData<>();

    public ReviewViewModel(Application application) {
        super(application);
        reviewRepository = new ReviewRepository(application);
    }

    public void insert(Review review) {
        reviewRepository.insert(review);
    }

    public void update(Review review) {
        reviewRepository.update(review);
    }

    public void delete(Review review) {
        reviewRepository.delete(review);
    }

    public LiveData<List<Review>> getReviewsForLocation(int locationId) {
        return reviewRepository.getReviewsForLocation(locationId);
    }

    public MutableLiveData<Double> getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(MutableLiveData<Double> averageRating) {
        this.averageRating = averageRating;
    }

    public MutableLiveData<Integer> getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(MutableLiveData<Integer> numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }
}
