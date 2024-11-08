package com.example.tripbuddy.API;

import android.os.AsyncTask;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostApiTask extends AsyncTask<String, Void, String> {

    private final String token;

    public PostApiTask(String token) {
        this.token = token;  // Store the token
    }

    @Override
    protected String doInBackground(String... params) {
        String apiUrl = params[0];
        String jsonInputString = params[1];  // JSON data to send in POST body

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json; utf-8");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Authorization", "Bearer " + token); // Set the bearer token
            urlConnection.setDoOutput(true);  // Allow sending body data
            urlConnection.setConnectTimeout(5000);  // Timeout after 5 seconds

            // Send the JSON data in the request body
            try (OutputStream os = urlConnection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                return "Success!";
            } else {
                return "Error: " + responseCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        // Handle the API response here
        System.out.println("API Response: " + result);
    }
}
