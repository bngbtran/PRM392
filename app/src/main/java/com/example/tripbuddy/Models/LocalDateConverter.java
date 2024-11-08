package com.example.tripbuddy.Models;

import androidx.room.TypeConverter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateConverter {
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @TypeConverter
    public static String fromLocalDate(LocalDate date) {
        return date == null ? null : date.format(dateFormatter);
    }

    @TypeConverter
    public static LocalDate toLocalDate(String dateString) {
        return dateString == null ? null : LocalDate.parse(dateString, dateFormatter);
    }
}

