package com.example.tripbuddy.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.tripbuddy.Models.Schedule;

import java.util.List;

@Dao
public interface ScheduleDAO {

    @Insert
    void insertSchedule(Schedule schedule);

    @Query("SELECT * FROM schedule WHERE userId = :userId ORDER BY date, time")
    LiveData<List<Schedule>> getUserSchedules(int userId);

    @Query("SELECT * FROM schedule WHERE date = :date")
    List<Schedule> getSchedulesByDate(String date);

    @Query("SELECT * FROM schedule WHERE date = :date")
    LiveData<List<Schedule>> getSchedulesByDateLive(String date);

    @Query("DELETE FROM schedule WHERE scheduleId = :id")
    void deleteSchedule(int id);
}
