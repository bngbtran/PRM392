package com.example.tripbuddy.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripbuddy.R;

import java.util.List;

import com.example.tripbuddy.Models.Notification;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<Notification> notifications;

    
    public NotificationAdapter(List<Notification> notifications) {
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        
        Notification notification = notifications.get(position);
        holder.title.setText(notification.getTitle());
        holder.description.setText(notification.getSubtitle());
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    
    public void updateData(List<Notification> newNotifications) {
        notifications = newNotifications;
        notifyDataSetChanged();
    }

    
    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;

        public NotificationViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.notification_Title);
            description = itemView.findViewById(R.id.subtitle);
        }
    }
}
