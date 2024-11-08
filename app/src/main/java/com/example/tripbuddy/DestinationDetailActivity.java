package com.example.tripbuddy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;


import com.bumptech.glide.Glide;
import com.example.tripbuddy.Adapters.UserSession;
import com.example.tripbuddy.ViewModel.BookMarkViewModel;
import com.example.tripbuddy.ViewModel.ReviewViewModel;
import com.example.tripbuddy.ViewModel.ScheduleViewModel;
import com.example.tripbuddy.ViewModel.UserViewModel;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class DestinationDetailActivity extends AppCompatActivity {
    private ReviewViewModel reviewViewModel;
    private boolean isExpanded = false;
    private ScheduleViewModel scheduleViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_detail);

        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> finish());

        // Get data from the intent
        Intent intent = getIntent();
        String destinationName = intent.getStringExtra("destinationName");
        int destinationId = intent.getIntExtra("destinationId", 0);
        String destinationLocation = intent.getStringExtra("destinationLocation");
        String destinationImageUrl = intent.getStringExtra("destinationImageUrl");
        String destinationDescription = intent.getStringExtra("destinationDescription");
        double destinationLatitude = intent.getDoubleExtra("destinationLatitude", 0);
        double destinationLongitude = intent.getDoubleExtra("destinationLongitude", 0);

        // Set the data to the views
        TextView titleTextView = findViewById(R.id.destinationTitle);
        TextView locationTextView = findViewById(R.id.location);
        TextView aboutDescription = findViewById(R.id.aboutDescription);
        ImageView headerImage = findViewById(R.id.headerImage);

        titleTextView.setText(destinationName);
        locationTextView.setText(destinationLocation);
        Glide.with(this).load(destinationImageUrl).into(headerImage);

        String shortDescription = destinationDescription.length() > 20 ? destinationDescription.substring(0, 20) + "..." : destinationDescription;

        aboutDescription.setText(addClickablePartTextViewResizable(shortDescription, destinationDescription, aboutDescription, "Read more..."));
        aboutDescription.setMovementMethod(LinkMovementMethod.getInstance());


        // Combine the average rating and number of ratings
        TextView ratingText = findViewById(R.id.ratingText);

        // Set an OnClickListener to show the full-screen image when clicked
        headerImage.setOnClickListener(v -> showFullScreenImage(destinationImageUrl));

        locationTextView.setOnClickListener(v -> openGoogleMaps(destinationLatitude, destinationLongitude));

        ratingText.setOnClickListener(v -> {
            Intent reviewsIntent = new Intent(DestinationDetailActivity.this, ReviewsActivity.class);
            reviewsIntent.putExtra("destinationId", destinationId); // Pass the destination ID
            reviewsIntent.putExtra("destinationName", destinationName); // Pass the name for display
            startActivity(reviewsIntent);
        });

        BookMarkViewModel bookMarkViewModel = new ViewModelProvider(this).get(BookMarkViewModel.class);

        ImageView bookmarkBtn = findViewById(R.id.bookmarkIcon);
        bookmarkBtn.setOnClickListener(view -> {
            // Assume you have the userId and destinationId
            bookMarkViewModel.addBookmark(destinationId, UserSession.getInstance().getUser().getUserId());
            Toast.makeText(this, "Bookmarked!", Toast.LENGTH_SHORT).show();
        });

        Button addToScheduleButton = findViewById(R.id.addToScheduleButton);
        addToScheduleButton.setOnClickListener(v ->{
            showAddToScheduleDialog(UserSession.getInstance().getUser().getUserId(), destinationId);
        });
    }

    private SpannableStringBuilder addClickablePartTextViewResizable(final String shortDescription, final String fullDescription,
                                                                     final TextView textView, final String expandableText) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(shortDescription + " " + expandableText);

        if (ssb.toString().contains(expandableText)) {
            ssb.setSpan(new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    toggleDescription(textView, shortDescription, fullDescription, expandableText);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(getResources().getColor(R.color.ic_launcher_background)); // Update color based on your theme
                    ds.setUnderlineText(false);
                }
            }, shortDescription.length() + 1, ssb.length(), 0);
        }
        return ssb;
    }

    private void toggleDescription(TextView textView, String shortDescription, String fullDescription, String expandableText) {
        if (!isExpanded) {
            textView.setText(String.format("%s Read less", fullDescription));
            isExpanded = true;
        } else {
            textView.setText(String.format("%s %s", shortDescription, expandableText));
            isExpanded = false;
        }

        textView.setText(addClickablePartTextViewResizable(
                fullDescription,
                shortDescription,
                textView,
                isExpanded ? "Read less" : "Read more..."));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void openGoogleMaps(double latitude, double longitude) {
        String uri = "geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }

    public void showFullScreenImage(String imageUrl) {
        final Dialog dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen); // Set to full screen
        dialog.setContentView(R.layout.dialog_full_screen_image);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent); // Make background transparent

        ImageView fullScreenImageView = dialog.findViewById(R.id.fullScreenImageView);
        Glide.with(this).load(imageUrl).into(fullScreenImageView); // Load the image with Glide
        fullScreenImageView.setOnClickListener(v -> dialog.dismiss()); // Dismiss the dialog when clicked

        dialog.show();
    }

    private void showAddToScheduleDialog(int userId, int destinationId) {
        // Inflate the custom dialog layout
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_to_schedule, null);
        scheduleViewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);

        // Initialize the DatePicker and TimePicker
        DatePicker datePicker = dialogView.findViewById(R.id.datePicker);
        TimePicker timePicker = dialogView.findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true); // Optional: Set 24-hour format

        // Create the AlertDialog
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setTitle("Add to Schedule")
                .setNegativeButton("Cancel", (d, which) -> d.dismiss())
                .create();

        // Handle "Add to Schedule" button click
        Button buttonAddToSchedule = dialogView.findViewById(R.id.buttonAddToSchedule);
        buttonAddToSchedule.setOnClickListener(v -> {
            // Get selected date
            int year = datePicker.getYear();
            int month = datePicker.getMonth();
            int day = datePicker.getDayOfMonth();
            LocalDate date = LocalDate.of(year, month + 1, day); // Month is 0-based
            // Get selected time
            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();
            LocalTime time = LocalTime.of(hour, minute);

            // Add to Schedule using ViewModel
            scheduleViewModel.addSchedule(userId, destinationId, date, time);

            // Show confirmation message
            Toast.makeText(this, "Added to schedule", Toast.LENGTH_SHORT).show();

            // Dismiss dialog
            dialog.dismiss();
        });

        // Show the dialog
        dialog.show();
    }

}
