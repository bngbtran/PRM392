package com.example.tripbuddy.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripbuddy.Models.Destination;
import com.example.tripbuddy.Models.Schedule;
import com.example.tripbuddy.R;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {
    private List<Schedule> scheduledItems; // Change this to a list of Schedule
    private List<Destination> destinations; // This will hold fetched destinations
    // Listener interface for check-in clicks
    private OnCheckInClickListener checkInClickListener;

    public ScheduleAdapter(List<Schedule> scheduledItems, List<Destination> destinations, OnCheckInClickListener listener) {
        this.scheduledItems = scheduledItems;
        this.destinations = destinations;
        this.checkInClickListener = listener;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_destination_schedule, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        Schedule schedule = scheduledItems.get(position);
        // Find the destination by ID
        Destination destination = findDestinationById(schedule.getDestinationId());

        if (destination != null) {
            holder.destinationNameTextView.setText(destination.getName());
            holder.checkInDateTextView.setText(String.format(" %s", schedule.getDate()));
            holder.checkInTimeTextView.setText(String.format(" %s", schedule.getTime()));

            // Set an OnClickListener for the check-in button
            holder.checkInButton.setOnClickListener(v -> {
                // Handle check-in logic here
                Toast.makeText(v.getContext(), "Checked in at " + destination.getName(), Toast.LENGTH_SHORT).show();
            });
        }

        holder.checkInButton.setOnClickListener(v -> {
            if (checkInClickListener != null) {
                checkInClickListener.onCheckInClick(schedule); // Notify listener when button is clicked
            }
        });
    }

    // Method to update schedules
    @SuppressLint("NotifyDataSetChanged")
    public void updateSchedules(List<Schedule> newSchedules) {
        // Update the schedule list with new data
        this.scheduledItems = newSchedules;
        // Notify the adapter that the data has changed
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return scheduledItems.size();
    }

    private Destination findDestinationById(int destinationId) {
        for (Destination destination : destinations) {
            if (destination.getId() == destinationId) {
                return destination;
            }
        }
        return null; // Return null if not found
    }

    public static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        public TextView destinationNameTextView;
        public TextView checkInDateTextView;
        public TextView checkInTimeTextView;
        public Button checkInButton;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            destinationNameTextView = itemView.findViewById(R.id.destinationNameTextView);
            checkInDateTextView = itemView.findViewById(R.id.checkInDateTextView);
            checkInTimeTextView = itemView.findViewById(R.id.checkInTimeTextView);
            checkInButton = itemView.findViewById(R.id.checkInButton);
        }
    }

    public interface OnCheckInClickListener {
        void onCheckInClick(Schedule schedule);
    };
}
