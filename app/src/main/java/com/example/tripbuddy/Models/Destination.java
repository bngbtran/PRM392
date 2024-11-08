package com.example.tripbuddy.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Destination")
public class Destination implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String location;
    private String imageResId;
    private String description;
    private double longitude;
    private double latitude;

    public Destination(String name, String location, String imageResId,String description, double longitude, double latitude) {
        this.name = name;
        this.location = location;
        this.imageResId = imageResId;
        this.description = description;
        this.longitude = longitude;
        this.latitude  = latitude;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getLongitude() {
        return longitude;
    }
    public double getLatitude() {
        return latitude;
    }
    public String getLocation() {
        return location;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setImageResId(String imageResId) {
        this.imageResId = imageResId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getImageResId() {
        return imageResId;
    }
}