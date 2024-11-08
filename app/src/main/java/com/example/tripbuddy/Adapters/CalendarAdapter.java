package com.example.tripbuddy.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripbuddy.Models.CalendarDate;
import com.example.tripbuddy.R;

import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {

    private List<CalendarDate> calendarDates;
    private OnDateClickListener onDateClickListener;
    int selectedPosition = -1;
    public CalendarAdapter(List<CalendarDate> calendarDates, OnDateClickListener onDateClickListener) {
        this.calendarDates = calendarDates;
        this.onDateClickListener = onDateClickListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.calendar_date_item, parent, false);
        return new CalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CalendarDate date = calendarDates.get(holder.getAdapterPosition());
        holder.dayOfWeekTextView.setText(date.getDayOfWeek());
        holder.dayTextView.setText(date.getDay());

        // Set background based on selected position
        if (selectedPosition == position) {
            holder.itemView.setBackgroundResource(R.drawable.circle_indicator_active); // Set green background for selected date
        } else {
            holder.itemView.setBackgroundResource(R.color.white);  // Default background for unselected dates
        }

        holder.itemView.setOnClickListener(v -> {
            selectedPosition = position;
            onDateClickListener.onDateClick(date); // Notify click listener
            notifyDataSetChanged(); // Refresh the adapter to highlight the selected item
        });
    }

    @Override
    public int getItemCount() {
        return calendarDates.size();
    }

    public static class CalendarViewHolder extends RecyclerView.ViewHolder {
        TextView dayOfWeekTextView, dayTextView;

        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);
            dayOfWeekTextView = itemView.findViewById(R.id.dayOfWeekTextView);
            dayTextView = itemView.findViewById(R.id.dateTextView);
        }
    }

    public interface OnDateClickListener {
        void onDateClick(CalendarDate date);
    }
}
