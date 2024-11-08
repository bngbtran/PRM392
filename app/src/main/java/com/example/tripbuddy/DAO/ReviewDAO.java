package com.example.tripbuddy.DAO;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tripbuddy.Models.Review;

import java.util.List;
@Dao
public interface ReviewDAO {
    @Insert
    void insert(Review review);

    @Update
    void update(Review review);

    @Delete
    void delete(Review review);

    @Query("SELECT * FROM Review WHERE locationId = :locationId ORDER BY reviewTime DESC")
    List<Review> getReviewsForLocation(int locationId); // New method
}


