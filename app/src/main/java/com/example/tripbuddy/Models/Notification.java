package com.example.tripbuddy.Models;

public class Notification {
    private final String title;
    private final String subtitle;
    private final String timestamp;
    private final int profileImageResId;

    public Notification(String title, String subtitle, String timestamp, int profileImageResId) {
        this.title = title;
        this.subtitle = subtitle;
        this.timestamp = timestamp;
        this.profileImageResId = profileImageResId;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getProfileImageResId() {
        return profileImageResId;
    }
}
