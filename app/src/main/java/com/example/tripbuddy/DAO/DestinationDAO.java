package com.example.tripbuddy.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.tripbuddy.Models.Destination;

import java.util.List;
@Dao
public interface DestinationDAO {
    @Insert
    void insert(Destination destination);

    @Update
    void update(Destination destination);

    @Delete
    void delete(Destination destination);

    @Query("SELECT * FROM Destination ORDER BY name ASC")
    LiveData<List<Destination>> getAllDestination();

    @Query("SELECT * FROM Destination WHERE id = :id LIMIT 1")
    Destination getDestinationById(int id);

    @Query("SELECT * FROM Destination WHERE name LIKE '%' || :name || '%' ORDER BY name ASC")
    LiveData<List<Destination>> searchDestination(String name);
}
