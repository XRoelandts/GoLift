package com.example.golift.workout;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.golift.R;

import java.util.ArrayList;
import java.util.List;


public class workoutsFrag extends Fragment {

   Button newWork;

    ArrayList<String> exercisesArr = new ArrayList();

   LinearLayout LL;

    String API_KEY = "9Ee9p+BL2PYmtPt/l+z/Kw==8wLM62Mk5VKuBDCc";


    public workoutsFrag() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_workouts, container, false);

        newWork = view.findViewById(R.id.newWorkB);
        newWork.setOnClickListener(addnewListener);

        LL = view.findViewById(R.id.LL);

        return view;
    }

    View.OnClickListener addnewListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            buildDialog();
        }
    };

    private void buildDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.dialog_create_workout, null);

        // Ids
        ListView selectedExLV = view.findViewById(R.id.exerciseLV);
        EditText workoutTitle = view.findViewById(R.id.workoutNameET);
        Spinner exerciseSpinner = view.findViewById(R.id.workoutSpinner);

        // Temp exercise array
        ArrayList<String> curExercises = new ArrayList<>();

        // Populate spinner
        makeRequest("biceps");
        makeRequest("lats");
        makeRequest("triceps");
        makeRequest("back");
        makeRequest("chest");
        makeRequest("hamstrings");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, exercisesArr);
        exerciseSpinner.setAdapter(spinnerAdapter);

        // Spinner listener
        exerciseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // adds selected exercise to list
               curExercises.add(exercisesArr.get(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // ListView listener
        selectedExLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Removes the selected exercise from the list
                curExercises.remove(position);
            }
        });

        // Submit Button
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = workoutTitle.getText().toString();

                addCard(name, curExercises);
            }
        });
        // Cancel button
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();

        dialog.show();

    }

    private void addCard(String name, ArrayList<String> exercises) {
        View view = getLayoutInflater().inflate(R.layout.card_workout, null);

        TextView title = view.findViewById(R.id.titleTV);
        title.setText(name);

        ListView exerciseLV = view.findViewById(R.id.exerciseLV);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, exercises);
        exerciseLV.setAdapter(adapter);

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // set lv visibility
                if(exerciseLV.getVisibility() == GONE) {
                    exerciseLV.setVisibility(VISIBLE);
                } else {
                    exerciseLV.setVisibility(GONE);
                }
            }
        });

        Button editB = view.findViewById(R.id.editB);

        editB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        LL.addView(view);

    }

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

                for (Exercise exercise : exercises) {
                    exercisesArr.add(exercise.getName());
                }

            }

            @Override
            public void onError(ANError anError) {
                // handle error

            }
        });
    }
}