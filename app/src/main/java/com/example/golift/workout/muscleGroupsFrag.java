package com.example.golift.workout;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.golift.R;


public class muscleGroupsFrag extends Fragment {

    ListView lv;
    CardView cv1;

    public muscleGroupsFrag() {
        // Required empty public constructor
    }


    // listener for when the card is clicked, to expand the listView
    View.OnClickListener cv1Listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //lv.setVisibility(VISIBLE);

        }
    };


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
        View view =  inflater.inflate(R.layout.fragment_muscle_groups, container, false);

        cv1 = view.findViewById(R.id.backCard);
        cv1.setOnClickListener(cv1Listener);

        lv = view.findViewById(R.id.bicepLV);
        // ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), null, null);
        // lv.setAdapter(adapter);



        return view;
    }
}