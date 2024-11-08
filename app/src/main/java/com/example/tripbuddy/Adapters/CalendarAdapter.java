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

    int selectedPosition = -1;
    private final List<CalendarDate> calendarDates;
    private final OnDateClickListener onDateClickListener;

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

        
        if (selectedPosition == position) {
            holder.itemView.setBackgroundResource(R.drawable.circle_indicator_active); 
        } else {
            holder.itemView.setBackgroundResource(R.color.white);  
        }

        holder.itemView.setOnClickListener(v -> {
            selectedPosition = position;
            onDateClickListener.onDateClick(date); 
            notifyDataSetChanged(); 
        });
    }

    @Override
    public int getItemCount() {
        return calendarDates.size();
    }

    public interface OnDateClickListener {
        void onDateClick(CalendarDate date);
    }

    public static class CalendarViewHolder extends RecyclerView.ViewHolder {
        TextView dayOfWeekTextView, dayTextView;

        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);
            dayOfWeekTextView = itemView.findViewById(R.id.dayOfWeekTextView);
            dayTextView = itemView.findViewById(R.id.dateTextView);
        }
    }
}
