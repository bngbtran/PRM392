package com.example.tripbuddy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripbuddy.Adapters.QuestionAdapter;
import com.example.tripbuddy.Models.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_question_page, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.questionRecyclerView);
        // Set up your adapter, data, etc. here
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Sample data
        List<Question> questionList = new ArrayList<>();
        questionList.add(new Question("How can I create my travel schedule ?", "To create your travel schedule, simply log in, get access to the \"Schedules\" section on the navigation bar. You will be processed to a schedule, just choose the date you want to travel, click \"Create Plan\" and choose any places you want."));
        questionList.add(new Question("What features does this system offer ?", "The system offers large amounts of destinations, many interesting places with real reviews from other people and so on. Besides, you can manage your trip by planning a schedule for yourself, making sure that you will have a memorable trip."));
        questionList.add(new Question("Who developed this system ?", "This system was developed by \"TripBuddy\" team consisting of 5 software engineers from FPT University, focusing on simplifying trip planning. You can know more about them by getting access to \"About Us\" on the navigation bar."));
        questionList.add(new Question("How user - friendly is the system for first - time travelers ?", "The system is designed to be user - friendly for first-time travelers, with an intuitive interface and helpful features and guides to make planning simple."));
        questionList.add(new Question("How can I handle the quality of each destination ?", "You can evaluate destinations based on factual and verified user reviews, ratings, and suggestions to ensure a high - quality experience during your trip."));
        questionList.add(new Question("Can I use this system on another platform ?", "Yes, the system is available on multiple platforms, including web browsers and Android smartphones."));

        QuestionAdapter questionAdapter = new QuestionAdapter(questionList);
        recyclerView.setAdapter(questionAdapter);
        return view;
    }
}
