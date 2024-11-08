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
    private RecyclerView recyclerViewSchedules; 
    private ScheduleAdapter scheduleAdapter; 
    private List<Schedule> schedules; 
    private List<Destination> destinations;
    private ScheduleViewModel scheduleViewModel;
    private CalendarAdapter calendarAdapter;
    private LocalDate currentDateLocalDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        
        View view = inflater.inflate(R.layout.activity_calendar, container, false);

        selectedDateTextView = view.findViewById(R.id.selectedDateTextView);
        recyclerViewCalendar = view.findViewById(R.id.recyclerViewCalendar);
        recyclerViewSchedules = view.findViewById(R.id.recyclerViewDestinations); 
        schedules = new ArrayList<>(); 
        destinations = new ArrayList<>(); 
        fetchDestinations();
        
        if (currentDateLocalDate == null)
            currentDateLocalDate = LocalDate.now(); 
        initializeCalendarDates(currentDateLocalDate);

        
        setupButtonListeners(view);

        return view;
    }

    private void fetchDestinations() {
        
        DestinationViewModel destinationViewModel = new ViewModelProvider(this).get(DestinationViewModel.class);

        
        destinationViewModel.getAllDestinations().observe(getViewLifecycleOwner(), destinationsList -> {
            if (destinationsList != null) {
                destinations.clear(); 
                destinations.addAll(destinationsList); 
            } else {
                
                destinations.clear(); 
            }
        });
    }

    private void initializeCalendarDates(LocalDate currentDateLocalDate) {
        
        List<CalendarDate> calendarDates = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            LocalDate date = currentDateLocalDate.plusDays(i);
            boolean isSelected = (i == 0); 
            calendarDates.add(new CalendarDate(
                    date.getDayOfWeek().toString().substring(0, 2), 
                    String.valueOf(date.getDayOfMonth()), 
                    String.valueOf(date.getDayOfMonth()), 
                    isSelected 
            ));
        }

        
        calendarAdapter = new CalendarAdapter(calendarDates, date -> {
            
            String selectedDate = String.format("%s %s %d",
                    date.getDay(),
                    currentDateLocalDate.getMonth(),
                    currentDateLocalDate.getYear());

            selectedDateTextView.setText(selectedDate);

            
            fetchSchedulesForDate(currentDateLocalDate.plusDays(calendarDates.indexOf(date)));

            
            Toast.makeText(getContext(), "Selected Date: " + selectedDate, Toast.LENGTH_SHORT).show();

            
            updateSelection(calendarDates, date);
        });

        
        recyclerViewCalendar.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCalendar.setAdapter(calendarAdapter);
    }

    
    @SuppressLint("NotifyDataSetChanged")
    private void updateSelection(List<CalendarDate> calendarDates, CalendarDate selectedDate) {
        for (CalendarDate calendarDate : calendarDates) {
            calendarDate.setSelected(calendarDate.equals(selectedDate)); 
        }
        currentDateLocalDate = currentDateLocalDate.plusDays(calendarDates.indexOf(selectedDate));
        calendarAdapter.notifyDataSetChanged(); 
    }

    private void fetchSchedulesForDate(LocalDate selectedDate) {
        schedules.clear(); 

        scheduleViewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);
        scheduleViewModel.getSchedulesByDate(selectedDate).observe(getViewLifecycleOwner(), fetchedSchedules -> {
            if (fetchedSchedules != null) {
                this.schedules.clear(); 
                this.schedules.addAll(fetchedSchedules); 

                
                if (scheduleAdapter == null) {
                    scheduleAdapter = new ScheduleAdapter(this.schedules, destinations, this);
                    recyclerViewSchedules.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerViewSchedules.setAdapter(scheduleAdapter);
                } else {
                    scheduleAdapter.updateSchedules(this.schedules); 
                }
            } else {
                this.schedules.clear(); 
                if (scheduleAdapter != null) {
                    scheduleAdapter.updateSchedules(this.schedules);
                }
            }
        });
    }


    private void setupButtonListeners(View view) {
        
        ImageView backButton = view.findViewById(R.id.backButton);
        TextView cancelButton = view.findViewById(R.id.textCancel);

        
        backButton.setOnClickListener(this::onClick);
        cancelButton.setOnClickListener(this::onClick);
    }

    private void onClick(View v) {
        
        HomeActivity secondFragment = new HomeActivity();
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, secondFragment) 
                .addToBackStack(null) 
                .commit();

        
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home);
    }

    public void onCheckInButtonClick(Schedule selectedSchedule) {
        if (selectedSchedule != null) {
            
            History history = new History(
                    selectedSchedule.getScheduleId(), 
                    selectedSchedule.getDestinationId(), 
                    LocalDateTime.now().toString(), 
                    selectedSchedule.getUserId() 
            );

            
            HistoryViewModel historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
            historyViewModel.insertHistory(history);

            
            deleteSchedule(selectedSchedule, currentDateLocalDate);
            
            Toast.makeText(getContext(), "Check-in successful!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "No schedule selected!", Toast.LENGTH_SHORT).show();
        }
    }

    
    private void deleteSchedule(Schedule schedule, LocalDate selectedDate) {
        
        ScheduleViewModel scheduleViewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);
        scheduleViewModel.deleteSchedule(schedule.getScheduleId());

        
        fetchSchedulesForDate(selectedDate); 
    }

    @Override
    public void onCheckInClick(Schedule selectedSchedule) {
        onCheckInButtonClick(selectedSchedule); 
    }
}
