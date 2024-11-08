package com.example.tripbuddy.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.tripbuddy.Models.BookMarkDestination;

import java.util.List;

@Dao
public interface BookMarkDAO {
    @Insert
    void insert(BookMarkDestination bookMarkDestination);

    @Query("SELECT * FROM BookMarkDestination WHERE userId = :userId")
    List<BookMarkDestination> getBookmarksForUser(int userId);

    @Query("DELETE FROM BookMarkDestination WHERE userId = :userId AND destinationId = :destinationId")
    void removeBookmark(int userId, int destinationId);
}
