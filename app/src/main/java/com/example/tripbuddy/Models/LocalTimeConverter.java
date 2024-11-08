package com.example.tripbuddy.Models;

import androidx.room.TypeConverter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeConverter {
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    @TypeConverter
    public static String fromLocalTime(LocalTime time) {
        return time == null ? null : time.format(timeFormatter);
    }

    @TypeConverter
    public static LocalTime toLocalTime(String timeString) {
        return timeString == null ? null : LocalTime.parse(timeString, timeFormatter);
    }
}
