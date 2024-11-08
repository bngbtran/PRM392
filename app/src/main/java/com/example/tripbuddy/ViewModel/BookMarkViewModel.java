package com.example.tripbuddy.ViewModel;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tripbuddy.Models.BookMarkDestination;
import com.example.tripbuddy.Repository.BookMarkRepository;

import java.util.List;

public class BookMarkViewModel extends AndroidViewModel {
    private BookMarkRepository bookMarkRepository;
    private LiveData<List<BookMarkDestination>> bookmarks;

    public BookMarkViewModel(@NonNull Application application) {
        super(application);
        bookMarkRepository = new BookMarkRepository(application);
    }

    public void addBookmark(int destinationId, int userId) {
        BookMarkDestination bookMarkDestination = new BookMarkDestination(destinationId, userId);
        bookMarkRepository.addBookmark(bookMarkDestination);
    }

    public LiveData<List<BookMarkDestination>> getUserBookmarks(int userId) {
        return bookMarkRepository.getUserBookmarks(userId);
    }
}

