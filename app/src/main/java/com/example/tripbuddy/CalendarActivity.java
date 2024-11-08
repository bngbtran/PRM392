package com.example.tripbuddy;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripbuddy.Adapters.CalendarAdapter;
import com.example.tripbuddy.Adapters.ScheduleAdapter;
import com.example.tripbuddy.Models.CalendarDate;
import com.example.tripbuddy.Models.Destination;
import com.example.tripbuddy.Models.History;
import com.example.tripbuddy.Models.Schedule;
import com.example.tripbuddy.ViewModel.HistoryViewModel;
import com.example.tripbuddy.ViewModel.ScheduleViewModel;
import com.example.tripbuddy.ViewModel.DestinationViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CalendarActivity extends Fragment implements ScheduleAdapter.OnCheckInClickListener {

    private TextView selectedDateTextView;
    private RecyclerView recyclerViewCalendar;
    private RecyclerView recyclerViewSchedules; // New RecyclerView for schedules
    private ScheduleAdapter scheduleAdapter; // Adapter for schedules
    private List<Schedule> schedules; // List to hold schedules
    private List<Destination> destinations;
    private ScheduleViewModel scheduleViewModel;// List to hold destinations
    private CalendarAdapter calendarAdapter;
    private LocalDate currentDateLocalDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_calendar, container, false);

        selectedDateTextView = view.findViewById(R.id.selectedDateTextView);
        recyclerViewCalendar = view.findViewById(R.id.recyclerViewCalendar);
        recyclerViewSchedules = view.findViewById(R.id.recyclerViewDestinations); // Initialize the schedules RecyclerView
        schedules = new ArrayList<>(); // Initialize schedules list
        destinations = new ArrayList<>(); // Initialize destinations list
        fetchDestinations();
        // Initialize ViewModel
        if(currentDateLocalDate == null)  currentDateLocalDate = LocalDate.now(); // Use today's date
        initializeCalendarDates(currentDateLocalDate);

        // Setup button listeners
        setupButtonListeners(view);

        return view;
    }

    private void fetchDestinations() {
        // Initialize the ViewModel
        DestinationViewModel destinationViewModel = new ViewModelProvider(this).get(DestinationViewModel.class);

        // Observe the LiveData for destinations
        destinationViewModel.getAllDestinations().observe(getViewLifecycleOwner(), destinationsList -> {
            if (destinationsList != null) {
                destinations.clear(); // Clear the current list to avoid duplicates
                destinations.addAll(destinationsList); // Add new destinations
            } else {
                // Handle the case when no destinations are found
                destinations.clear(); // Clear the adapter or set an empty list
            }
        });
    }

    private void initializeCalendarDates(LocalDate currentDateLocalDate) {
        // Generate calendar dates for the week
        List<CalendarDate> calendarDates = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            LocalDate date = currentDateLocalDate.plusDays(i);
            boolean isSelected = (i == 0); // Mark the first date as selected
            calendarDates.add(new CalendarDate(
                    date.getDayOfWeek().toString().substring(0, 2), // Day of the week abbreviation
                    String.valueOf(date.getDayOfMonth()), // Day of the month
                    String.valueOf(date.getDayOfMonth()), // Day of the month
                    isSelected // Is it the selected date?
            ));
        }

        // Create an instance of the CalendarAdapter
        calendarAdapter = new CalendarAdapter(calendarDates, date -> {
            // Fetch the selected date
            String selectedDate = String.format("%s %s %d",
                    date.getDay(),
                    currentDateLocalDate.getMonth(),
                    currentDateLocalDate.getYear());

            selectedDateTextView.setText(selectedDate);

            // Fetch and display schedules for the selected date
            fetchSchedulesForDate(currentDateLocalDate.plusDays(calendarDates.indexOf(date)));

            // Optional: show a toast when the date is clicked
            Toast.makeText(getContext(), "Selected Date: " + selectedDate, Toast.LENGTH_SHORT).show();

            // Update selection state
            updateSelection(calendarDates, date);
        });

        // Set up RecyclerView with horizontal layout
        recyclerViewCalendar.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCalendar.setAdapter(calendarAdapter);
    }

    // Method to update selection state in the adapter
    @SuppressLint("NotifyDataSetChanged")
    private void updateSelection(List<CalendarDate> calendarDates, CalendarDate selectedDate) {
        for (CalendarDate calendarDate : calendarDates) {
            calendarDate.setSelected(calendarDate.equals(selectedDate)); // Update selection state
        }
        currentDateLocalDate = currentDateLocalDate.plusDays(calendarDates.indexOf(selectedDate));
        calendarAdapter.notifyDataSetChanged(); // Notify the adapter of data changes
    }

    private void fetchSchedulesForDate(LocalDate selectedDate) {
        schedules.clear(); // Clear existing schedules

        scheduleViewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);
        scheduleViewModel.getSchedulesByDate(selectedDate).observe(getViewLifecycleOwner(), fetchedSchedules -> {
            if (fetchedSchedules != null) {
                this.schedules.clear(); // Clear any old schedules
                this.schedules.addAll(fetchedSchedules); // Add new schedules

                // Initialize or update the adapter
                if (scheduleAdapter == null) {
                    scheduleAdapter = new ScheduleAdapter(this.schedules, destinations, this);
                    recyclerViewSchedules.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerViewSchedules.setAdapter(scheduleAdapter);
                } else {
                    scheduleAdapter.updateSchedules(this.schedules); // Update existing adapter with new data
                }
            } else {
                this.schedules.clear(); // Clear the adapter or set an empty list
                if (scheduleAdapter != null) {
                    scheduleAdapter.updateSchedules(this.schedules);
                }
            }
        });
    }



    private void setupButtonListeners(View view) {
        // Existing button listeners for navigating the calendar...
        ImageView backButton = view.findViewById(R.id.backButton);
        TextView cancelButton = view.findViewById(R.id.textCancel);

        // Handle back button click
        backButton.setOnClickListener(this::onClick);
        cancelButton.setOnClickListener(this::onClick);
    }

    private void onClick(View v) {
        // Chuyển đổi sang SecondFragment
        HomeActivity secondFragment = new HomeActivity();
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, secondFragment) // fragment_container là ID của container trong Activity
                .addToBackStack(null) // Tùy chọn thêm vào backstack để quay lại fragment trước đó
                .commit();

        // Cập nhật mục đã chọn trên BottomNavigationView
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home);
    }

    public void onCheckInButtonClick(Schedule selectedSchedule) {
        if (selectedSchedule != null) {
            // Create a History instance
            History history = new History(
                    selectedSchedule.getScheduleId(), // scheduleId
                    selectedSchedule.getDestinationId(), // destinationId
                    LocalDateTime.now().toString(), // checkInDate, use your preferred date format
                    selectedSchedule.getUserId() // userId
            );

            // Insert the history record
            HistoryViewModel historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
            historyViewModel.insertHistory(history);

            // Implement this method to remove the schedule
            deleteSchedule(selectedSchedule, currentDateLocalDate);
            // Notify the user
            Toast.makeText(getContext(), "Check-in successful!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "No schedule selected!", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to delete a schedule
    private void deleteSchedule(Schedule schedule, LocalDate selectedDate) {
        // Assuming you have a method in your ScheduleViewModel to delete schedules
        ScheduleViewModel scheduleViewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);
        scheduleViewModel.deleteSchedule(schedule.getScheduleId());

        // Update the adapter or notify changes to your RecyclerView
        fetchSchedulesForDate(selectedDate); // Or whatever method refreshes your schedule list
    }

    @Override
    public void onCheckInClick(Schedule selectedSchedule) {
        onCheckInButtonClick(selectedSchedule); // This calls your existing check-in logic
    }
}
