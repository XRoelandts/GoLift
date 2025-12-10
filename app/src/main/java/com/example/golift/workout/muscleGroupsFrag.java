package com.example.golift.workout;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.renderscript.RenderScript;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.golift.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class muscleGroupsFrag extends Fragment {

    // Variables
    String API_KEY = "9Ee9p+BL2PYmtPt/l+z/Kw==8wLM62Mk5VKuBDCc";
    ArrayList<String> exerciseList = new ArrayList<>();
    ArrayAdapter<String> adapter;

    // XML Variables

    // Biceps
    ListView lv1;
    CardView cv1;

    // Back
    ListView lv2;
    CardView cv2;

    // Chest
    ListView lv3;
    CardView cv3;

    // Legs
    ListView lv4;
    CardView cv4;

    // Shoulders
    ListView lv5;
    CardView cv5;

    // Triceps
    ListView lv6;
    CardView cv6;


    // Constructor

    public muscleGroupsFrag() {
        // Required empty public constructor
    }


    // Card Listeners
    // Expand the card to show the listview
    View.OnClickListener cv1Listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            expandLV(lv1, "biceps");

        }
    };
    View.OnClickListener cv2Listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            expandLV(lv2, "lats");

        }
    };
    View.OnClickListener cv3Listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            expandLV(lv3, "chest");

        }
    };
    View.OnClickListener cv4Listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            expandLV(lv4, "hamstrings");

        }
    };
    View.OnClickListener cv5Listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            expandLV(lv5, "traps");

        }
    };
    View.OnClickListener cv6Listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            expandLV(lv6, "triceps");

        }
    };

    // Helper Methods

    // Expand Selected LV
    public void expandLV(ListView LV, String muscle) {
        closeAll();
        if(!(LV.getVisibility() == VISIBLE)) {
            makeRequest(muscle);

            LV.setVisibility(VISIBLE);

            ViewGroup.LayoutParams params = LV.getLayoutParams();
            params.height = 1300;
            LV.setLayoutParams(params);
            LV.requestLayout();
        } else {
            LV.setVisibility(GONE);
        }

    }

    // Close All LV
    public void closeAll() {
        lv1.setVisibility(GONE);
        lv2.setVisibility(GONE);
        lv3.setVisibility(GONE);
        lv4.setVisibility(GONE);
        lv5.setVisibility(GONE);
        lv6.setVisibility(GONE);

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
        View view =  inflater.inflate(R.layout.fragment_muscle_groups, container, false);

        // Finding IDs and setting listeners
        cv1 = view.findViewById(R.id.bicepsCard);
        cv1.setOnClickListener(cv1Listener);

        lv1 = view.findViewById(R.id.bicepLV);
        lv1.setVisibility(GONE);

        cv2 = view.findViewById(R.id.backCard);
        cv2.setOnClickListener(cv2Listener);

        lv2 = view.findViewById(R.id.backLV);
        lv2.setVisibility(GONE);

        cv3 = view.findViewById(R.id.chestCard);
        cv3.setOnClickListener(cv3Listener);

        lv3 = view.findViewById(R.id.chestLV);
        lv3.setVisibility(GONE);

        cv4 = view.findViewById(R.id.legsCard);
        cv4.setOnClickListener(cv4Listener);

        lv4 = view.findViewById(R.id.legsLV);
        lv4.setVisibility(GONE);

        cv5 = view.findViewById(R.id.shouldersCard);
        cv5.setOnClickListener(cv4Listener);

        lv5 = view.findViewById(R.id.shouldersLV);
        lv5.setVisibility(GONE);

        cv6 = view.findViewById(R.id.tricepscard);
        cv6.setOnClickListener(cv4Listener);

        lv6 = view.findViewById(R.id.tricepsLV);
        lv6.setVisibility(GONE);

        adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, exerciseList);

        lv1.setAdapter(adapter);
        lv2.setAdapter(adapter);
        lv3.setAdapter(adapter);
        lv4.setAdapter(adapter);
        lv5.setAdapter(adapter);
        lv6.setAdapter(adapter);




        return view;
    }


    // key : 9Ee9p+BL2PYmtPt/l+z/Kw==8wLM62Mk5VKuBDCc
    private void makeRequest(String muscle) {
        ArrayList<String> list = new ArrayList<>();

        ANRequest req = AndroidNetworking.get("https://api.api-ninjas.com/v1/exercises")
                .addQueryParameter("muscle", muscle)
                .addHeaders("X-Api-Key", API_KEY)
                .setPriority(Priority.LOW)
                .build();


        req.getAsObjectList(Exercise.class, new ParsedRequestListener<List<Exercise>>() {
            @Override
            public void onResponse(List<Exercise> exercises) {


                exerciseList.clear();
                for (Exercise exercise : exercises) {
                    exerciseList.add(exercise.getName());
                }

                adapter.notifyDataSetChanged();


            }

            @Override
            public void onError(ANError anError) {
                // handle error

            }
        });
    }







}