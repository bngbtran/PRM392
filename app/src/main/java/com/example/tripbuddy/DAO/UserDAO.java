package com.example.tripbuddy.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Insert;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.tripbuddy.Models.User;

@Dao
public interface UserDAO {
    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM User WHERE username = :username AND password = :password LIMIT 1")
    LiveData<User> getUser(String username, String password);

    @Query("SELECT password FROM User WHERE email = :email LIMIT 1")
    LiveData<String> getPasswordByUsername(String email);
}
