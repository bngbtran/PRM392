package com.example.tripbuddy.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tripbuddy.AppDatabase.AppDatabase;
import com.example.tripbuddy.DAO.ReviewDAO;
import com.example.tripbuddy.Models.Review;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReviewRepository {
    private final ReviewDAO reviewDao;
    public ExecutorService executorService;

    public ReviewRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        reviewDao = db.reviewDAO();
        executorService = Executors.newFixedThreadPool(2);
    }

    public void insert(Review review) {
        // Perform insertion in a background thread
        new Thread(() -> reviewDao.insert(review)).start();
    }

    public void update(Review review) {
        new Thread(() -> reviewDao.update(review)).start();
    }

    public void delete(Review review) {
        new Thread(() -> reviewDao.delete(review)).start();
    }

    public LiveData<List<Review>> getReviewsForLocation(int locationId) {
        MutableLiveData<List<Review>> reviewsLiveData = new MutableLiveData<>();
        new Thread(() -> {
            List<Review> reviews = reviewDao.getReviewsForLocation(locationId);
            reviewsLiveData.postValue(reviews);
        }).start();
        return reviewsLiveData;
    }

    public List<Review> getReviewsForLocations(int locationId){
        return reviewDao.getReviewsForLocation(locationId);
    }
}
