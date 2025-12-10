package com.example.golift.saved;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.example.golift.R;
import com.example.golift.model.Gym;
import com.example.golift.pageButtonsFragment;
import com.example.golift.search.gymContentProvider;

import java.util.ArrayList;
import java.util.List;

public class SavedActivity extends AppCompatActivity implements SavedGymsAdapter.OnGymActionListener {

    FragmentManager fg;
    ViewPager2 viewPager;
    SavedGymsAdapter adapter;
    ImageButton btnPrevious;
    ImageButton btnNext;
    List<Gym> savedGyms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_saved);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        viewPager = findViewById(R.id.viewPagerSaved);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);

        // Set up adapter
        adapter = new SavedGymsAdapter(this);
        viewPager.setAdapter(adapter);

        // Load saved gyms from database
        loadSavedGyms();

        // Set up arrow button listeners
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = viewPager.getCurrentItem();
                if (currentItem > 0) {
                    viewPager.setCurrentItem(currentItem - 1, true);
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = viewPager.getCurrentItem();
                if (currentItem < adapter.getItemCount() - 1) {
                    viewPager.setCurrentItem(currentItem + 1, true);
                }
            }
        });

        // Update arrow button visibility based on current position
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updateArrowButtons(position);
            }
        });

        // Set up page transformer for card effect
        viewPager.setPageTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float scaleFactor = Math.max(0.85f, 1 - Math.abs(position) * 0.15f);
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
                page.setAlpha(Math.max(0.5f, 1 - Math.abs(position)));
            }
        });

        // Add bottom navigation fragment
        if (savedInstanceState == null) {
            fg = getSupportFragmentManager();
            FragmentTransaction trans = fg.beginTransaction();
            pageButtonsFragment pageButtons = new pageButtonsFragment();
            trans.add(R.id.buttonFragments, pageButtons, "buttonsFrag");
            trans.commit();
        }

        // Initial arrow button state
        updateArrowButtons(0);
    }

    private void loadSavedGyms() {
        savedGyms = new ArrayList<>();

        // Step 1: Get all bookmarked gym IDs
        Cursor bookmarkCursor = getContentResolver().query(
                bookmarkedContentProvider.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        if (bookmarkCursor != null) {
            try {
                int gymIdIndex = bookmarkCursor.getColumnIndex(bookmarkedContentProvider.COL_GYM_ID);

                while (bookmarkCursor.moveToNext()) {
                    long gymId = bookmarkCursor.getLong(gymIdIndex);

                    // Step 2: Get full gym details from gymContentProvider
                    Cursor gymCursor = getContentResolver().query(
                            gymContentProvider.CONTENT_URI,
                            null,
                            "_id = ?",
                            new String[]{String.valueOf(gymId)},
                            null
                    );

                    if (gymCursor != null) {
                        try {
                            if (gymCursor.moveToFirst()) {
                                int nameIndex = gymCursor.getColumnIndex(gymContentProvider.COL_NAME);
                                int distanceIndex = gymCursor.getColumnIndex(gymContentProvider.COL_DISTANCE);
                                int descriptionIndex = gymCursor.getColumnIndex(gymContentProvider.COL_DESCRIPTION);
                                int idIndex = gymCursor.getColumnIndex("_id");

                                long id = gymCursor.getLong(idIndex);
                                String name = gymCursor.getString(nameIndex);
                                int distance = gymCursor.getInt(distanceIndex);
                                String description = gymCursor.getString(descriptionIndex);

                                Gym gym = new Gym(id, name, distance, description);
                                savedGyms.add(gym);
                            }
                        } finally {
                            gymCursor.close();
                        }
                    }
                }
            } finally {
                bookmarkCursor.close();
            }
        }

        // Update adapter with loaded gyms
        adapter.setGymList(savedGyms);

        // Show message if no saved gyms
        if (savedGyms.isEmpty()) {
            Toast.makeText(this, "No saved gyms yet!", Toast.LENGTH_LONG).show();
        }
    }

    private void updateArrowButtons(int position) {
        // Hide previous button on first item
        btnPrevious.setVisibility(position == 0 ? View.INVISIBLE : View.VISIBLE);

        // Hide next button on last item
        btnNext.setVisibility(position == adapter.getItemCount() - 1 ? View.INVISIBLE : View.VISIBLE);

        // Hide both if no items
        if (adapter.getItemCount() == 0) {
            btnPrevious.setVisibility(View.INVISIBLE);
            btnNext.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onRemoveGym(Gym gym, int position) {
        // Delete ONLY from bookmarks table (gym still exists in gymContentProvider)
        int deletedRows = getContentResolver().delete(
                bookmarkedContentProvider.CONTENT_URI,
                bookmarkedContentProvider.COL_GYM_ID + " = ?",
                new String[]{String.valueOf(gym.getId())}
        );

        if (deletedRows > 0) {
            // Remove from adapter
            adapter.removeGym(position);
            savedGyms.remove(position);

            Toast.makeText(this, "Gym removed from saved (still in search)", Toast.LENGTH_SHORT).show();

            // Update arrow buttons after removal
            int newPosition = Math.min(position, adapter.getItemCount() - 1);
            if (newPosition >= 0) {
                viewPager.setCurrentItem(newPosition, false);
            }
            updateArrowButtons(newPosition);

            // Show message if no more gyms
            if (adapter.getItemCount() == 0) {
                Toast.makeText(this, "No more saved gyms", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Failed to remove gym", Toast.LENGTH_SHORT).show();
        }
    }
}