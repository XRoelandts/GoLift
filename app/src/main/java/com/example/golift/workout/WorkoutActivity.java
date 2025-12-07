package com.example.golift.workout;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.golift.R;
import com.example.golift.pageButtonsFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class WorkoutActivity extends AppCompatActivity {

    FragmentManager fg;

    ViewPager VP;

    TabLayout tabLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_workout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (savedInstanceState == null) {
            fg = getSupportFragmentManager();
            FragmentTransaction trans = fg.beginTransaction();
            pageButtonsFragment pageButtons = new pageButtonsFragment();
            trans.add(R.id.buttonFragments, pageButtons, "buttonsFrag");

            trans.commit();
        }

        tabLayout = findViewById(R.id.workoutsTabLayout);


        // adapter to allow viewpager to switch between fragments
        FragmentStateAdapter adapter = new FragmentStateAdapter(this ) {
            // createFragment gets the position from whichever tab is selected and returns the fragment
            // that needs to be displayed
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                switch (position) {
                    case 0:
                        return new workoutsFrag();
                    case 1:
                        return new muscleGroupsFrag();
                    default:
                        // default should return a functional fragment to ensure no errors
                        return new muscleGroupsFrag();
                }

            }


            // returns the total number of fragments
            // is how tablayoutmediator knows how many tabs to create
            @Override
            public int getItemCount() {
                return 2;
            }
        };

        ViewPager2 vp = findViewById(R.id.VP);
        vp.setAdapter(adapter);


        // allows the viewpager to interface with the tablayout so that clicking a tab switches the fragment viewed
        /*
        " (tab, position) -> " is a lambda expression
        labda expressions are a shorthand for creating a method
        the content inside the parenthesis are the parameters
        the content after the -> is the function itself
        in this case it takes the parameter position and then uses that to set the text of the tabs
        then it actually attatches it to the viewpager

         */
        new TabLayoutMediator(tabLayout, vp, (tab, position) -> {

            switch (position) {
                case 0:
                    tab.setText("My Workouts"); // Set title for the first tab
                    break;
                case 1:
                    tab.setText("Muscle Groups"); // Set title for the second tab
                    break;
                // Add more cases if you have more tabs
            }
        }).attach();
    }

    // add request for workout api, thinking of using JSONObject use code from brock Assignment 5
    // https://www.api-ninjas.com/api/exercises - link to api

}