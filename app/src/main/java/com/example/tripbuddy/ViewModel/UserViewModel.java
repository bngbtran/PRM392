package com.example.tripbuddy.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.tripbuddy.Repository.UserRepository;
import com.example.tripbuddy.Models.User;
import java.util.concurrent.ExecutorService;

public class UserViewModel extends AndroidViewModel {
    private UserRepository repository;
    public ExecutorService executorService;
    private LiveData<String> passwordLiveData;
    public UserViewModel(Application application) {
        super(application);
        repository = new UserRepository(application);
    }

    public LiveData<User> getUser(String username, String password) {
        return repository.getUser(username, password);
    }

    public void insert(User user){
        repository.insert(user);
    }

    public LiveData<String> getPasswordByEmail(String email) {
        passwordLiveData = repository.getPasswordByUsername(email);
        return passwordLiveData;
    }
}
