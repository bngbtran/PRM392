package com.example.tripbuddy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripbuddy.Adapters.DestinationAdapter;
import com.example.tripbuddy.Adapters.UserSession;
import com.example.tripbuddy.Models.Destination;
import com.example.tripbuddy.ViewModel.DestinationViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends Fragment {

    private final List<Destination> destinationList = new ArrayList<>();
    private RecyclerView destinationRecyclerView;
    private DestinationAdapter destinationAdapter;
    private DestinationViewModel destinationViewModel;

    public HomeActivity() {
        super(R.layout.activity_home);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false); // Inflate the layout for this fragment

        // Set username
        TextView username = view.findViewById(R.id.textHeader);
        String name = UserSession.getInstance().getUser().getUsername();
        TextView viewAll = view.findViewById(R.id.viewAllText);
        username.setText(name);

        ImageView profileImageView = view.findViewById(R.id.profileImageView);
        ImageView notificationImageView = view.findViewById(R.id.notificationIcon);

        // Setup RecyclerView
        destinationRecyclerView = view.findViewById(R.id.destinationRecyclerView);
        destinationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Initialize ViewModel
        destinationViewModel = new DestinationViewModel(requireActivity().getApplication());

        // Load destination data
        loadDestinationData();

        // Setup click listeners for other UI elements (like profile, notifications)
        profileImageView.setOnClickListener(v -> {
            ProfileActivity secondFragment = new ProfileActivity();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, secondFragment)
                    .addToBackStack(null)
                    .commit();
            BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView);
            bottomNavigationView.setSelectedItemId(R.id.profile);
        });

        notificationImageView.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), NotificationActivity.class);
            startActivity(intent);
        });

        viewAll.setOnClickListener(v -> {
            // Navigate to SearchActivity
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new SearchActivity()) // Replace with your SearchActivity
                    .addToBackStack(null) // Add to back stack to allow back navigation
                    .commit();
        });
        return view;
    }

    private void loadDestinationData() {
        destinationViewModel.getAllDestinations().observe(getViewLifecycleOwner(), new Observer<List<Destination>>() {
            @Override
            public void onChanged(List<Destination> destinations) {
                if (destinations != null && !destinations.isEmpty()) {
                    destinationList.clear();
                    // Get only the first three destinations
                    destinationList.addAll(destinations.subList(0, Math.min(3, destinations.size())));
                    updateRecyclerView();
                } else {
                    Toast.makeText(getContext(), "No destinations found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void updateRecyclerView() {
        if (destinationAdapter == null) {
            destinationAdapter = new DestinationAdapter(destinationList, this.getContext());
            destinationRecyclerView.setAdapter(destinationAdapter);
        } else {
            destinationAdapter.notifyDataSetChanged();
        }
    }

}
