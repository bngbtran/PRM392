package com.example.tripbuddy.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.tripbuddy.AppDatabase.AppDatabase;
import com.example.tripbuddy.DAO.HistoryDAO;
import com.example.tripbuddy.Models.History;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HistoryRepository {
    private HistoryDAO historyDao;
    private LiveData<List<History>> allHistory;
    public ExecutorService executorService;

    public HistoryRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        historyDao = db.historyDAO();
        executorService = Executors.newFixedThreadPool(2);
    }

    public void insertHistory(History history) {
        new Thread(() -> historyDao.insert(history)).start(); // Using a new thread for database operations
    }

    public LiveData<List<History>> getHistoryByUserId(int userId) {
        MutableLiveData<List<History>> historyData = new MutableLiveData<>();
        new Thread(() -> historyData.postValue(historyDao.getHistoryByUserId(userId))).start(); // Using a new thread
        return historyData;
    }

}
