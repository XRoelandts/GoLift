package com.example.golift.search;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.golift.R;
import com.example.golift.pageButtonsFragment;
import com.example.golift.saved.bookmarkedContentProvider;

public class SearchActivity extends AppCompatActivity implements OnBookmarkButtonClickListener {

    FragmentManager fg;
    gymContentProvider gymProvider;
    bookmarkedContentProvider bookmarkProvider;
    Uri uri = gymContentProvider.CONTENT_URI;

    SearchView search;
    ListView gymView;

    gymsViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            // ... your insets code ...
            return insets;
        });

        if (savedInstanceState == null) {
            fg = getSupportFragmentManager();
            FragmentTransaction trans = fg.beginTransaction();
            pageButtonsFragment pageButtons = new pageButtonsFragment();
            trans.add(R.id.buttonFragments, pageButtons, "buttonsFrag");
            trans.commit();
        }

        gymProvider = new gymContentProvider();
        populateContent(); // Your method is called here
        bookmarkProvider = new bookmarkedContentProvider();

        search = findViewById(R.id.gymSearch);

        gymView = findViewById(R.id.gymLV);
        Cursor data = getContentResolver().query(uri, null, null, null, null);

        String[] mListColumns = new String[]{gymContentProvider.COL_NAME, gymContentProvider.COL_DISTANCE};
        int[] mListItems = new int[]{R.id.gymNameTV, R.id.gymDistTV};

        adapter = new gymsViewAdapter(this, R.layout.workoutscard, data, mListColumns, mListItems, 0, this);

        gymView.setAdapter(adapter);
    }

    @Override
    public void onBookmarkClick(Cursor cursor) {
        if (cursor == null) {
            return;
        }
        try {
            String gymName = cursor.getString(cursor.getColumnIndexOrThrow(gymContentProvider.COL_NAME));
            String gymDist = cursor.getString(cursor.getColumnIndexOrThrow(gymContentProvider.COL_DISTANCE));

            Toast.makeText(this, "Bookmarked: " + gymName, Toast.LENGTH_SHORT).show();

            ContentValues values = new ContentValues();
            values.put(bookmarkedContentProvider.COL_NAME, gymName);
            values.put(bookmarkedContentProvider.COL_DISTANCE, gymDist);

            getContentResolver().insert(bookmarkedContentProvider.CONTENT_URI, values);

        } catch (IllegalArgumentException e) {
            Log.e("SearchActivity", "Error getting column from cursor: " + e.getMessage());
        }
    }


public void populateContent() {
        Cursor data = getContentResolver().query(uri, null, null, null, null, null);
        if(data.getCount() == 0) {
            ContentValues values = new ContentValues();

            values.put("Name", "LA Fitness");
            values.put("Distance", "2.3");
            values.put("Description", "");
            Uri newUri = getContentResolver().insert(uri, values);
            values.clear();

            values.put("Name", "Music City Muscle Gym");
            values.put("Distance", "3.6");
            values.put("Description", "");
            getContentResolver().insert(uri, values);
            values.clear();

            values.put("Name", "Downtown YMCA");
            values.put("Distance", "2.3");
            values.put("Description", "");
            getContentResolver().insert(uri, values);
            values.clear();

            values.put("Name", "Fit Factory Nashville");
            values.put("Distance", "1.9");
            values.put("Description", "");
            getContentResolver().insert(uri, values);
            values.clear();

            values.put("Name", "GetFit Anytime");
            values.put("Distance", "1.2");
            values.put("Description", "");
            getContentResolver().insert(uri, values);
            values.clear();

            values.put("Name", "STEPS Fitness");
            values.put("Distance", "0.6");
            values.put("Description", "");
            getContentResolver().insert(uri, values);
            values.clear();

            values.put("Name", "CROSSFIT PRVN NASHVILLE");
            values.put("Distance", "1.4");
            values.put("Description", "");
            getContentResolver().insert(uri, values);
            values.clear();

            values.put("Name", "Next Level Fitness");
            values.put("Distance", "2.2");
            values.put("Description", "");
            getContentResolver().insert(uri, values);
            values.clear();

            values.put("Name", "Fuse Fitness");
            values.put("Distance", "1.8");
            values.put("Description", "");
            getContentResolver().insert(uri, values);
            values.clear();

            values.put("Name", "Fitness: 1440 South");
            values.put("Distance", "4.0");
            values.put("Description", "");
            getContentResolver().insert(uri, values);
            values.clear();
        }
    }
}