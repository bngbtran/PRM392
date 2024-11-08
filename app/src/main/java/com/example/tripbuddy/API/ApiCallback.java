package com.example.tripbuddy.API;

public interface ApiCallback {
    void onSuccess(String result);
    void onError(String error);
}
