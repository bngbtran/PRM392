package com.example.tripbuddy.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tripbuddy.Models.History;
import com.example.tripbuddy.R;
import com.example.tripbuddy.ViewModel.DestinationViewModel;
import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<History> historyList = new ArrayList<>();
    private final DestinationViewModel destinationViewModel;
    private final LifecycleOwner lifecycleOwner;

    public HistoryAdapter(DestinationViewModel destinationViewModel, LifecycleOwner lifecycleOwner) {
        this.destinationViewModel = destinationViewModel;
        this.lifecycleOwner = lifecycleOwner;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        History history = historyList.get(position);
        holder.bind(history);
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public void setHistoryList(List<History> historyList) {
        this.historyList = historyList;
        notifyDataSetChanged();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {
        private final TextView checkInDateTextView;
        private final TextView destinationNameTextView;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            checkInDateTextView = itemView.findViewById(R.id.checkInDateTextView);
            destinationNameTextView = itemView.findViewById(R.id.destinationNameTextView);
        }

        public void bind(History history) {
            // Displaying check-in date
            checkInDateTextView.setText(history.getCheckInDate().substring(0, 10));

            // Observe destination details to update the destination name dynamically
            destinationViewModel.getDestinationById(history.getDestinationId())
                    .observe(lifecycleOwner, destination -> {
                        if (destination != null) {
                            destinationNameTextView.setText(destination.getName());
                        } else {
                            destinationNameTextView.setText("Unknown Destination");
                        }
                    });
        }
    }
}
