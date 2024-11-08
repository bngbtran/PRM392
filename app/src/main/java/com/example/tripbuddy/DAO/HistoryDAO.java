package com.example.tripbuddy.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.tripbuddy.Models.History;

import java.util.List;

@Dao
public interface HistoryDAO {

    @Insert
    void insert(History history);

    @Query("SELECT * FROM History WHERE userId = :userId ORDER BY checkInDate DESC")
    List<History> getHistoryByUserId(int userId); // Fetch history by userId

    @Query("SELECT * FROM History WHERE userId = :userId ORDER BY checkInDate DESC")
    LiveData<List<History>> getHistoryByUserIdLiveData(int userId);

    @Query("DELETE FROM History WHERE id = :id")
    void deleteById(int id);
}
