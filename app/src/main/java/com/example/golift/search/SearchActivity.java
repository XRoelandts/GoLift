package com.example.golift.search;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.golift.R;
import com.example.golift.pageButtonsFragment;
import com.example.golift.saved.BookmarkHelper;
import com.example.golift.saved.bookmarkedContentProvider;

public class SearchActivity extends AppCompatActivity {

    FragmentManager fg;
    gymContentProvider gymProvider;
    SimpleCursorAdapter adapter;
    gymsViewAdapter adapter1;
    bookmarkedContentProvider bookmarkProvider;

    Uri uri = gymContentProvider.CONTENT_URI;
    Uri bookmarkUri = bookmarkedContentProvider.CONTENT_URI;

    SearchView search;
    ListView gymView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
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

        // Provider setup
        gymProvider = new gymContentProvider();
        bookmarkProvider = new bookmarkedContentProvider();

        // Populate gyms if database is empty
        populateContent();

        // Search setup
        search = findViewById(R.id.gymSearch);
        search.clearFocus();
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter gyms by name
                String selection = gymContentProvider.COL_NAME + " LIKE ?";
                String[] selectionArgs = new String[]{"%" + newText + "%"};

                Cursor filteredCursor = getContentResolver().query(uri, null, selection, selectionArgs, null);
                adapter1.changeCursor(filteredCursor);

                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
        });

        // List View setup
        gymView = findViewById(R.id.gymLV);

        Cursor data = getContentResolver().query(uri, null, null, null, null, null);

        if(data != null && data.getCount() > 0) {
            data.moveToFirst();
            Log.i("data test", data.getString(data.getColumnIndexOrThrow(gymContentProvider.COL_NAME)));
        }

        String[] mListColumns = new String[] {
                gymContentProvider.COL_NAME,
                gymContentProvider.COL_DISTANCE
        };
        int[] mListItems = new int[] {
                R.id.gymNameTV,
                R.id.gymDistTV
        };

        adapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.workoutscard, data, mListColumns, mListItems);
        adapter1 = new gymsViewAdapter(SearchActivity.this, R.layout.workoutscard, data, mListColumns, mListItems);

        gymView.setAdapter(adapter1);
    }

    /**
     * Bookmark a gym by storing only its ID reference
     * @param gymId The ID of the gym from the cursor
     * @param position Position in the list (optional, for logging)
     */
    public void bookmarkGym(long gymId, int position) {
        Log.i("bookmark", "Attempting to bookmark gym ID: " + gymId);

        // Use BookmarkHelper to add bookmark
        boolean success = BookmarkHelper.addBookmark(this, gymId);

        if (success) {
            // Refresh the adapter to update bookmark button states
            adapter1.notifyDataSetChanged();
            Log.i("bookmark", "Successfully bookmarked gym ID: " + gymId);
        }
    }

    /**
     * Alternative method if you only have the gym name and need to find the ID
     * @param gymName Name of the gym
     * @param position Position in the cursor
     */
    public void bookmarkGymByName(String gymName, int position) {
        Log.i("bookmark", "Bookmarking gym: " + gymName);

        // Query to find the gym by name and get its ID
        Cursor data = getContentResolver().query(
                uri,
                new String[]{"_id", gymContentProvider.COL_NAME},
                gymContentProvider.COL_NAME + " = ?",
                new String[]{gymName},
                null
        );

        if (data != null && data.moveToFirst()) {
            int idIndex = data.getColumnIndexOrThrow("_id");
            long gymId = data.getLong(idIndex);
            data.close();

            // Now bookmark using the gym ID
            bookmarkGym(gymId, position);
        } else {
            Toast.makeText(this, "Gym not found", Toast.LENGTH_SHORT).show();
            if (data != null) {
                data.close();
            }
        }
    }

    /**
     * Remove bookmark
     * @param gymId The ID of the gym to unbookmark
     */
    public void removeBookmark(long gymId) {
        BookmarkHelper.removeBookmark(this, gymId);
        adapter1.notifyDataSetChanged();
    }

    /**
     * Toggle bookmark status
     * @param gymId The ID of the gym
     */
    public void toggleBookmark(long gymId) {
        BookmarkHelper.toggleBookmark(this, gymId);
        adapter1.notifyDataSetChanged();
    }

    /**
     * Check if a gym is bookmarked (useful for updating UI)
     * @param gymId The ID of the gym
     * @return true if bookmarked
     */
    public boolean isGymBookmarked(long gymId) {
        return BookmarkHelper.isBookmarked(this, gymId);
    }

    public void populateContent() {
        Cursor data = getContentResolver().query(uri, null, null, null, null, null);

        if(data != null && data.getCount() == 0) {
            ContentValues values = new ContentValues();

            values.put(gymContentProvider.COL_NAME, "LA Fitness");
            values.put(gymContentProvider.COL_DISTANCE, 2);
            values.put(gymContentProvider.COL_DESCRIPTION, "Full-service gym with cardio, weights, pool, and group classes");
            getContentResolver().insert(uri, values);
            values.clear();

            values.put(gymContentProvider.COL_NAME, "Music City Muscle Gym");
            values.put(gymContentProvider.COL_DISTANCE, 4);
            values.put(gymContentProvider.COL_DESCRIPTION, "Hardcore bodybuilding gym with heavy equipment and personal training");
            getContentResolver().insert(uri, values);
            values.clear();

            values.put(gymContentProvider.COL_NAME, "Downtown YMCA");
            values.put(gymContentProvider.COL_DISTANCE, 2);
            values.put(gymContentProvider.COL_DESCRIPTION, "Community fitness center with family programs and swimming facilities");
            getContentResolver().insert(uri, values);
            values.clear();

            values.put(gymContentProvider.COL_NAME, "Fit Factory Nashville");
            values.put(gymContentProvider.COL_DISTANCE, 2);
            values.put(gymContentProvider.COL_DESCRIPTION, "Modern 24/7 fitness facility with state-of-the-art equipment");
            getContentResolver().insert(uri, values);
            values.clear();

            values.put(gymContentProvider.COL_NAME, "GetFit Anytime");
            values.put(gymContentProvider.COL_DISTANCE, 1);
            values.put(gymContentProvider.COL_DESCRIPTION, "24-hour access gym with flexible membership options");
            getContentResolver().insert(uri, values);
            values.clear();

            values.put(gymContentProvider.COL_NAME, "STEPS Fitness");
            values.put(gymContentProvider.COL_DISTANCE, 1);
            values.put(gymContentProvider.COL_DESCRIPTION, "Boutique fitness studio specializing in HIIT and functional training");
            getContentResolver().insert(uri, values);
            values.clear();

            values.put(gymContentProvider.COL_NAME, "CROSSFIT PRVN NASHVILLE");
            values.put(gymContentProvider.COL_DISTANCE, 1);
            values.put(gymContentProvider.COL_DESCRIPTION, "CrossFit box with certified coaches and community-focused training");
            getContentResolver().insert(uri, values);
            values.clear();

            values.put(gymContentProvider.COL_NAME, "Next Level Fitness");
            values.put(gymContentProvider.COL_DISTANCE, 2);
            values.put(gymContentProvider.COL_DESCRIPTION, "Personal training focused gym with customized workout programs");
            getContentResolver().insert(uri, values);
            values.clear();

            values.put(gymContentProvider.COL_NAME, "Fuse Fitness");
            values.put(gymContentProvider.COL_DISTANCE, 2);
            values.put(gymContentProvider.COL_DESCRIPTION, "Group training and boot camp style workouts for all fitness levels");
            getContentResolver().insert(uri, values);
            values.clear();

            values.put(gymContentProvider.COL_NAME, "Fitness: 1440 South");
            values.put(gymContentProvider.COL_DISTANCE, 4);
            values.put(gymContentProvider.COL_DESCRIPTION, "Comprehensive fitness center with spa amenities and nutrition coaching");
            getContentResolver().insert(uri, values);
            values.clear();

            Log.i("populateContent", "Added 10 gyms to database");
        }

        if (data != null) {
            data.close();
        }
    }
}