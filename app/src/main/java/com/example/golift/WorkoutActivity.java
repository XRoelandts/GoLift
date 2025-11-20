package com.example.golift;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class WorkoutActivity extends AppCompatActivity {

    FragmentManager fg;

    ViewPager vp;

    TabLayout tab;



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

        vp = findViewById(R.id.workoutVP);
        tab = findViewById(R.id.workoutTab);


    }
}