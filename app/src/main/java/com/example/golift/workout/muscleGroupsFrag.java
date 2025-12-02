package com.example.golift.workout;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.golift.R;


public class muscleGroupsFrag extends Fragment {

    ListView lv1;
    CardView cv1;
    ListView lv2;
    CardView cv2;
    ListView lv3;
    CardView cv3;
    ListView lv4;
    CardView cv4;



    public muscleGroupsFrag() {
        // Required empty public constructor
    }


    // listener for when the card is clicked, to expand the listView
    View.OnClickListener cv1Listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!(lv1.getVisibility() == VISIBLE)) {
                lv1.setVisibility(VISIBLE);
            } else {
                lv1.setVisibility(GONE);
            }

        }
    };
    View.OnClickListener cv2Listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!(lv2.getVisibility() == VISIBLE)) {
                lv2.setVisibility(VISIBLE);
            } else {
                lv2.setVisibility(GONE);
            }

        }
    };
    View.OnClickListener cv3Listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!(lv3.getVisibility() == VISIBLE)) {
                lv3.setVisibility(VISIBLE);
            } else {
                lv3.setVisibility(GONE);
            }

        }
    };
    View.OnClickListener cv4Listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!(lv4.getVisibility() == VISIBLE)) {
                lv4.setVisibility(VISIBLE);
            } else {
                lv4.setVisibility(GONE);
            }

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

        // Listeners
        cv1 = view.findViewById(R.id.bicepsCard);
        cv1.setOnClickListener(cv1Listener);

        lv1 = view.findViewById(R.id.bicepLV);

        cv2 = view.findViewById(R.id.backCard);
        cv2.setOnClickListener(cv2Listener);

        lv2 = view.findViewById(R.id.backLV);

        cv3 = view.findViewById(R.id.chestCard);
        cv3.setOnClickListener(cv3Listener);

        lv3 = view.findViewById(R.id.chestLV);

        cv4 = view.findViewById(R.id.legsCard);
        cv4.setOnClickListener(cv4Listener);

        lv4 = view.findViewById(R.id.legsLV);



        // Adapters
        /*
        ArrayAdapter adapter1 = new ArrayAdapter(getActivity(), );
        lv1.setAdapter(adapter1);

        ArrayAdapter adapter2 = new ArrayAdapter(getActivity(), );
        lv2.setAdapter(adapter2);

        ArrayAdapter adapter3 = new ArrayAdapter(getActivity(), );
        lv3.setAdapter(adapter3);

        ArrayAdapter adapter4 = new ArrayAdapter(getActivity(), );
        lv4.setAdapter(adapter4);

         */



        return view;
    }

    // API

    /*
    private void makeRequest(String idNum){
        ANRequest req = AndroidNetworking.get("https://pokeapi.co/api/v2/pokemon/{id}")
                .addPathParameter("id", idNum)
                //.addQueryParameter("apikey", API_KEY)
                .setPriority(Priority.LOW)
                .build();
        req.getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {

            }
            public void onError(ANError anError) {
                Toast.makeText(getApplicationContext(), "Error on getting data", Toast.LENGTH_LONG).show();
            }
        });

     */


}