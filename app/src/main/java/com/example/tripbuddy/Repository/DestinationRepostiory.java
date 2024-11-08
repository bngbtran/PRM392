package com.example.tripbuddy.Repository;

import android.app.Application;
import androidx.lifecycle.LiveData;


import com.example.tripbuddy.AppDatabase.AppDatabase;
import com.example.tripbuddy.DAO.DestinationDAO;
import com.example.tripbuddy.Models.Destination;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DestinationRepostiory {
    private DestinationDAO destinationDAO;
    private LiveData<List<Destination>> allDestination;
    public ExecutorService executorService;

    public DestinationRepostiory(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        destinationDAO = db.destinationDAO();
        allDestination = destinationDAO.getAllDestination();
        executorService = Executors.newFixedThreadPool(2);
    }

    public LiveData<List<Destination>> getAllDestination() {
        return allDestination;
    }

    public void insert(Destination food) {
        executorService.execute(() -> destinationDAO.insert(food));
    }

    public void update(Destination food) {
        executorService.execute(() -> destinationDAO.update(food));
    }

    public void delete(Destination food) {
        executorService.execute(() -> destinationDAO.delete(food));
    }

    public Destination getDestinationById(int id) {
        return destinationDAO.getDestinationById(id);
    }

}

