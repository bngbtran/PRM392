package com.example.tripbuddy.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tripbuddy.Models.Schedule;
import com.example.tripbuddy.Repository.ScheduleRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScheduleViewModel extends AndroidViewModel {
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final ScheduleRepository scheduleRepository;
    private LiveData<List<Schedule>> schedules;

    public ScheduleViewModel(Application application) {
        super(application);
        scheduleRepository = new ScheduleRepository(application);
    }

    public void addSchedule(int userId, int destinationId, LocalDate date, LocalTime time) {
        executorService.execute(() -> {
            Schedule schedule = new Schedule(userId, destinationId, date, time);
            scheduleRepository.addDestinationToSchedule(schedule);
        });
    }

    public LiveData<List<Schedule>> getSchedulesForUser(int userId) {
        return scheduleRepository.getUserSchedules(userId);
    }

    public void fetchSchedules(LocalDate date) {
        schedules = scheduleRepository.getSchedulesByDateLive(date);
    }

    public LiveData<List<Schedule>> getSchedules() {
        return schedules;
    }

    public LiveData<List<Schedule>> getSchedulesByDate(LocalDate date) {
        return scheduleRepository.getSchedulesByDateLive(date);
    }

    public void deleteSchedule(int schedule) {
        new Thread(() -> scheduleRepository.deleteSchedule(schedule)).start(); // Using a new thread for database operations
    }
}
