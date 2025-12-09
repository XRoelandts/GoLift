package com.example.golift.workout;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.golift.R;
import com.example.golift.pageButtonsFragment;
import com.google.android.material.tabs.TabLayoutMediator;

public class workoutCard extends CardView {




    public workoutCard(@NonNull Context context) {
        super(context);
        View view = new View(context);

        view.findViewById(R.id.editB);



    }
}
