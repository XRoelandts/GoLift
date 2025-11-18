package com.example.golift;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class pageButtonsFragment extends Fragment {


    Button homeB;
    Button searchB;
    Button workoutsB;
    Button profileB;
    Button savedB;


    public pageButtonsFragment() {
        // Required empty public constructor
    }

    View.OnClickListener onHomeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener onSavedListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(getActivity(), SavedActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener onSearchListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener onProfileListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(getActivity(), ProfileActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener onWorkoutsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(getActivity(), WorkoutActivity.class);
            startActivity(intent);
        }
    };
    public void changePage() {
        homeB.setBackgroundColor(getResources().getColor(R.color.orange));
        searchB.setBackgroundColor(getResources().getColor(R.color.white));
        savedB.setBackgroundColor(getResources().getColor(R.color.white));
        workoutsB.setBackgroundColor(getResources().getColor(R.color.white));
        profileB.setBackgroundColor(getResources().getColor(R.color.white));


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

        homeB = view.findViewById(R.id.homeB);
        searchB = view.findViewById(R.id.searchB);
        workoutsB = view.findViewById(R.id.workoutsB);
        profileB = view.findViewById(R.id.profileB);
        savedB = view.findViewById(R.id.savedB);

        return view;
    }
}