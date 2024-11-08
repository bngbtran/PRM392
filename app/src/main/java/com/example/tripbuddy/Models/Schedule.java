package com.example.tripbuddy.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity(tableName = "schedule")
public class Schedule {

    @PrimaryKey(autoGenerate = true)
    private int scheduleId;

    private int userId;
    private int destinationId;
    private LocalDate date;
    private LocalTime time;

    // Constructor, getters, and setters
    public Schedule(int userId, int destinationId, LocalDate date, LocalTime time) {
        this.userId = userId;
        this.destinationId = destinationId;
        this.date = date;
        this.time = time;
    }

    // Other getters and setters

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
