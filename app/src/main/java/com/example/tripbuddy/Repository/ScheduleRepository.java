package com.example.tripbuddy.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.tripbuddy.AppDatabase.AppDatabase;
import com.example.tripbuddy.DAO.ScheduleDAO;
import com.example.tripbuddy.Models.Schedule;

import java.time.LocalDate;

import java.util.List;


public class ScheduleRepository {
    private final ScheduleDAO scheduleDao;

    public ScheduleRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        scheduleDao = db.scheduleDAO();
    }

    
    public void addDestinationToSchedule(Schedule schedule) {
        scheduleDao.insertSchedule(schedule);
    }

    
    public LiveData<List<Schedule>> getUserSchedules(int userId) {
        return scheduleDao.getUserSchedules(userId);
    }


    
    public LiveData<List<Schedule>> getSchedulesByDateLive(LocalDate date) {
        String dateString = date.toString();
        return scheduleDao.getSchedulesByDateLive(dateString);
    }

    public void deleteSchedule(int schedule) {
        scheduleDao.deleteSchedule(schedule);
    }
}
