package com.example.tripbuddy.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripbuddy.DestinationDetailActivity;
import com.example.tripbuddy.R;
import com.example.tripbuddy.Models.Destination;
import com.bumptech.glide.Glide;

import java.util.List;

public class DestinationAdapter extends RecyclerView.Adapter<DestinationAdapter.ViewHolder> {
    private List<Destination> destinationList;
    private Context context;

    public DestinationAdapter(List<Destination> destinationList, Context context) {
        this.destinationList = destinationList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.destination_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Destination destination = destinationList.get(position);
        holder.destinationName.setText(destination.getName());
        holder.destinationLocation.setText(destination.getLocation());

        // Load image from URL using Glide
        Glide.with(context)
                .load(destination.getImageResId()) // Assuming imageResId is a URL
                .placeholder(R.drawable.sample_destination) // Placeholder image while loading
                .into(holder.destinationImage);

        // Handle click event on each destination item
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DestinationDetailActivity.class);
            intent.putExtra("destinationName", destination.getName());
            intent.putExtra("destinationId", destination.getId());
            intent.putExtra("destinationLocation", destination.getLocation());
            intent.putExtra("destinationImageUrl", destination.getImageResId());
            intent.putExtra("destinationDescription", destination.getDescription());
            intent.putExtra("destinationLatitude", destination.getLatitude());
            intent.putExtra("destinationLongitude", destination.getLongitude());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return destinationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView destinationName;
        TextView destinationLocation;
        ImageView destinationImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            destinationName = itemView.findViewById(R.id.destinationName);
            destinationLocation = itemView.findViewById(R.id.destinationLocation);
            destinationImage = itemView.findViewById(R.id.destinationImage);
        }
    }
}
