package com.example.tripbuddy.ViewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tripbuddy.Models.Destination;
import com.example.tripbuddy.Repository.DestinationRepostiory;

import java.util.List;

public class DestinationViewModel extends AndroidViewModel {
    private DestinationRepostiory repository;
    private LiveData<List<Destination>> allDestinations;
    private MutableLiveData<Destination> destination;

    public DestinationViewModel(@NonNull Application application) {
        super(application);
        repository = new DestinationRepostiory(application);
        allDestinations = repository.getAllDestination();
        destination = new MutableLiveData<>();
    }

    public LiveData<List<Destination>> getAllDestinations() {
        return allDestinations;
    }

    public LiveData<Destination> getDestinationById(int id) {
        loadDestinationById(id);
        return destination;
    }

    private void loadDestinationById(int id) {
        // Use a background thread to fetch data
        repository.executorService.execute(() -> {
            Destination fetchedFood = repository.getDestinationById(id);
            destination.postValue(fetchedFood);
        });
    }

    public void insert(Destination destination) {
        repository.insert(destination);
    }

    public void update(Destination destination) {
        repository.update(destination);
    }

    public void delete(Destination destination) {
        repository.executorService.execute(() -> {
            repository.delete(destination);
        });
    }
}
