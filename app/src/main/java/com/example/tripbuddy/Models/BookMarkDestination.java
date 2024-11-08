package com.example.tripbuddy.Models;

import androidx.room.Entity;

@Entity(tableName = "BookMarkDestination" , primaryKeys = {"userId", "destinationId"})
public class BookMarkDestination {
    private int destinationId;
    private int userId;

    public BookMarkDestination(int destinationId, int userId) {
        this.destinationId = destinationId;
        this.userId = userId;
    }

    public int getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
