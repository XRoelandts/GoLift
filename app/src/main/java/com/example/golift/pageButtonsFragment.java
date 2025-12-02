package com.example.golift;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.golift.search.SearchActivity;
import com.example.golift.profile.ProfileActivity;
import com.example.golift.saved.SavedActivity;
import com.example.golift.workout.WorkoutActivity;


public class pageButtonsFragment extends Fragment {


    Button homeB;
    Button searchB;
    Button workoutsB;
    Button profileB;
    Button savedB;


    public pageButtonsFragment() {
        // Required empty public constructor
    }



    // On Click Listeners
    View.OnClickListener onWorkoutsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            changePage(1);
            Intent intent = new Intent(getActivity(), WorkoutActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener onSearchListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            changePage(2);
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener onHomeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            changePage(3);
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener onSavedListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            changePage(4);
            Intent intent = new Intent(getActivity(), SavedActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener onProfileListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            changePage(5);
            Intent intent = new Intent(getActivity(), ProfileActivity.class);
            startActivity(intent);
        }
    };

    // Changes the color of the Buttons as pages are switched
    public void changePage(int num) {

        switch (num) {
            case 1:
                workoutsB.setBackgroundColor(getResources().getColor(R.color.orange));
                searchB.setBackgroundColor(getResources().getColor(R.color.white));
                homeB.setBackgroundColor(getResources().getColor(R.color.white));
                savedB.setBackgroundColor(getResources().getColor(R.color.white));
                profileB.setBackgroundColor(getResources().getColor(R.color.white));
                break;
            case 2:
                workoutsB.setBackgroundColor(getResources().getColor(R.color.white));
                searchB.setBackgroundColor(getResources().getColor(R.color.orange));
                homeB.setBackgroundColor(getResources().getColor(R.color.white));
                savedB.setBackgroundColor(getResources().getColor(R.color.white));
                profileB.setBackgroundColor(getResources().getColor(R.color.white));
                break;
            case 3:
                workoutsB.setBackgroundColor(getResources().getColor(R.color.white));
                searchB.setBackgroundColor(getResources().getColor(R.color.white));
                homeB.setBackgroundColor(getResources().getColor(R.color.orange));
                savedB.setBackgroundColor(getResources().getColor(R.color.white));
                profileB.setBackgroundColor(getResources().getColor(R.color.white));
                break;
            case 4:
                workoutsB.setBackgroundColor(getResources().getColor(R.color.white));
                searchB.setBackgroundColor(getResources().getColor(R.color.white));
                homeB.setBackgroundColor(getResources().getColor(R.color.white));
                savedB.setBackgroundColor(getResources().getColor(R.color.orange));
                profileB.setBackgroundColor(getResources().getColor(R.color.white));
                break;
            case 5:
                workoutsB.setBackgroundColor(getResources().getColor(R.color.white));
                searchB.setBackgroundColor(getResources().getColor(R.color.white));
                homeB.setBackgroundColor(getResources().getColor(R.color.white));
                savedB.setBackgroundColor(getResources().getColor(R.color.white));
                profileB.setBackgroundColor(getResources().getColor(R.color.orange));
                break;
        }

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_page_buttons, container, false);

        // Finding id of buttons
        homeB = view.findViewById(R.id.homeB);
        searchB = view.findViewById(R.id.searchB);
        workoutsB = view.findViewById(R.id.workoutsB);
        profileB = view.findViewById(R.id.profileB);
        savedB = view.findViewById(R.id.savedB);

        // Setting onClickListeners
        homeB.setOnClickListener(onHomeListener);
        searchB.setOnClickListener(onSearchListener);
        workoutsB.setOnClickListener(onWorkoutsListener);
        profileB.setOnClickListener(onProfileListener);
        savedB.setOnClickListener(onSavedListener);

        return view;
    }
}