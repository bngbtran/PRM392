package com.example.tripbuddy.Models;

public class CalendarDate {
    private String dayOfWeek;
    private String dayOfMonth;
    private String day;
    private boolean isSelected;

    public CalendarDate(String dayOfWeek,String dayOfMonth, String day, boolean isSelected) {
        this.dayOfWeek = dayOfWeek;
        this.day = day;
        this.isSelected = isSelected;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(String dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public String getDay() {
        return day;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
    // Override equals and hashCode for proper comparison
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CalendarDate)) return false;
        CalendarDate other = (CalendarDate) obj;
        return this.day.equals(other.day) && this.dayOfMonth.equals(other.dayOfMonth);
    }
}

