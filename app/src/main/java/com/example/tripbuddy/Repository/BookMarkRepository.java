package com.example.tripbuddy.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tripbuddy.AppDatabase.AppDatabase;
import com.example.tripbuddy.DAO.BookMarkDAO;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.example.tripbuddy.Models.BookMarkDestination;

public class BookMarkRepository {
    private BookMarkDAO bookMarkDao;
    public ExecutorService executorService;

    public BookMarkRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        bookMarkDao = db.bookMarkDao();
        executorService = Executors.newFixedThreadPool(2);
    }

    public void addBookmark(BookMarkDestination bookMarkDestination) {
        // Use a background thread to insert into the database
        new Thread(() -> bookMarkDao.insert(bookMarkDestination)).start();
    }

    public LiveData<List<BookMarkDestination>> getUserBookmarks(int userId) {
        // Retrieve bookmarks in a background thread
        MutableLiveData<List<BookMarkDestination>> bookmarksLiveData = new MutableLiveData<>();
        new Thread(() -> {
            List<BookMarkDestination> bookmarks = bookMarkDao.getBookmarksForUser(userId);
            bookmarksLiveData.postValue(bookmarks);
        }).start();
        return bookmarksLiveData;
    }
}
