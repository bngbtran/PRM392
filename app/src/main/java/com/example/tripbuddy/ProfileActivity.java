package com.example.tripbuddy;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tripbuddy.Adapters.UserSession;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends Fragment {


    public ProfileActivity() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_profile , container, false);
        ImageView backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(this::onClick);

        ImageView editTextView = view.findViewById(R.id.editProfileButton);
        String email = UserSession.getInstance().getUser().getEmail();
        String username = UserSession.getInstance().getUser().getUsername();
        TextView profileName = view.findViewById(R.id.profileName);
        profileName.setText(username);
        TextView profileEmail = view.findViewById(R.id.profileEmail);
        profileEmail.setText(username);
        editTextView.setOnClickListener(views -> {
           Intent intent = new Intent(getActivity(), EditProfileActivity.class);
           startActivity(intent);
        });

        ImageView previousTripIcon = view.findViewById(R.id.previousEndIcon);
        previousTripIcon.setOnClickListener(views -> {
            Intent intent = new Intent(getActivity(), HistoryActivity.class);
            startActivity(intent);
        });

        ImageView bookmarkEndIcon = view.findViewById(R.id.bookmarkEndIcon);
        bookmarkEndIcon.setOnClickListener(views -> {
            Intent intent = new Intent(getActivity(), BookmarkedActivity.class);
            startActivity(intent);
        });

        return view;
    }
    private void onClick(View v) {
        // Chuyển đổi sang SecondFragment
        HomeActivity secondFragment = new HomeActivity();
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, secondFragment) // fragment_container là ID của container trong Activity
                .addToBackStack(null) // Tùy chọn thêm vào backstack để quay lại fragment trước đó
                .commitAllowingStateLoss();

        // Cập nhật mục đã chọn trên BottomNavigationView
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home);
    }
}