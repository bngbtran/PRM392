package com.example.tripbuddy.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "History")
public class History {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int scheduleId;
    private int destinationId;
    private String checkInDate; // Use a suitable type for your date, e.g., LocalDateTime
    private int userId; // Optional, depending on your user model

    // Constructor
    public History(int scheduleId, int destinationId, String checkInDate, int userId) {
        this.scheduleId = scheduleId;
        this.destinationId = destinationId;
        this.checkInDate = checkInDate;
        this.userId = userId;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
