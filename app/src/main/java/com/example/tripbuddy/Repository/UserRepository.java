package com.example.tripbuddy.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;


import com.example.tripbuddy.AppDatabase.AppDatabase;
import com.example.tripbuddy.DAO.UserDAO;
import com.example.tripbuddy.Models.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {
    public ExecutorService executorService;
    private final UserDAO userDao;

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
        executorService = Executors.newFixedThreadPool(2);
    }

    public LiveData<User> getUser(String username, String password) {
        return userDao.getUser(username, password);
    }

    public void insert(User user) {
        executorService.execute(() -> userDao.insert(user));
    }

    public LiveData<String> getPasswordByUsername(String username) {
        return userDao.getPasswordByUsername(username);
    }
}

