package com.example.tripbuddy.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.tripbuddy.Models.History;
import com.example.tripbuddy.Repository.HistoryRepository;

import java.util.List;

public class HistoryViewModel extends AndroidViewModel {
    private final HistoryRepository historyRepository;

    public HistoryViewModel(Application application) {
        super(application);
        historyRepository = new HistoryRepository(application);
    }

    public void insertHistory(History history) {
        historyRepository.insertHistory(history);
    }

    public LiveData<List<History>> getHistoryByUserId(int userId) {
        return historyRepository.getHistoryByUserId(userId);
    }

}
