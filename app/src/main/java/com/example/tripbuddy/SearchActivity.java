package com.example.tripbuddy;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripbuddy.Adapters.DestinationAdapter;
import com.example.tripbuddy.Models.Destination;
import com.example.tripbuddy.ViewModel.DestinationViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends Fragment {

    private DestinationViewModel destinationViewModel;
    private RecyclerView destinationRecyclerView;
    private DestinationAdapter destinationAdapter;
    private List<Destination> allDestinations = new ArrayList<>();

    public SearchActivity() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_search, container, false);

        // Initialize RecyclerView
        destinationRecyclerView = view.findViewById(R.id.destinationRecyclerView);
        destinationRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Initialize ViewModel
        destinationViewModel = new ViewModelProvider(this).get(DestinationViewModel.class);

        // Observe all destinations
        destinationViewModel.getAllDestinations().observe(getViewLifecycleOwner(), new Observer<List<Destination>>() {
            @Override
            public void onChanged(List<Destination> destinations) {
                allDestinations.clear();
                allDestinations.addAll(destinations);
                updateRecyclerView(destinations);
            }
        });

        // Set Adapter
        destinationAdapter = new DestinationAdapter(allDestinations, getContext());
        destinationRecyclerView.setAdapter(destinationAdapter);

        // Set search bar listener
        TextView searchBar = view.findViewById(R.id.searchBar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterDestinations(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        // Set back button
        ImageView backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(this::onClick);

        // Set cancel button
        TextView cancelButton = view.findViewById(R.id.textCancel);
        cancelButton.setOnClickListener(this::onClick);

        return view;
    }

    private void updateRecyclerView(List<Destination> destinations) {
        destinationAdapter.notifyDataSetChanged();
    }

    private void filterDestinations(String query) {
        List<Destination> filteredList = new ArrayList<>();
        for (Destination destination : allDestinations) {
            if (destination.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(destination);
            }
        }
        destinationAdapter = new DestinationAdapter(filteredList, getContext());
        destinationRecyclerView.setAdapter(destinationAdapter);
    }

    private void onClick(View v) {
        HomeActivity secondFragment = new HomeActivity();
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, secondFragment)
                .addToBackStack(null)
                .commit();

        // Update selected item on BottomNavigationView
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home);
    }
}
