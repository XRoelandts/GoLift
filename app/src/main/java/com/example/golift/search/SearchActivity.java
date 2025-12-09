package com.example.golift.search;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.golift.R;
import com.example.golift.pageButtonsFragment;

public class SearchActivity extends AppCompatActivity {

    FragmentManager fg;

    gymContentProvider gymProvider;
    SimpleCursorAdapter adapter;

    Uri uri = gymContentProvider.CONTENT_URI;

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
        populateContent();


        search = findViewById(R.id.gymSearch);
        search.clearFocus();
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                // --- THIS IS THE NEW FILTERING LOGIC ---

                // Define the selection criteria. The '?' is a placeholder.
                // "LIKE ?" with "%" wildcards performs a "contains" search.
                String selection = gymContentProvider.COL_NAME + " LIKE ?";
                String[] selectionArgs = new String[]{"%" + newText + "%"};

                // Re-query the ContentProvider with the new filter.
                Cursor filteredCursor = getContentResolver().query(uri, null, selection, selectionArgs, null);

                // Tell the adapter to use the new, filtered cursor.
                // The adapter will automatically update the ListView.
                adapter.changeCursor(filteredCursor);

                return true; // We've handled the event
            }

            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }
        });

        gymView = findViewById(R.id.gymLV);

        Cursor data = getContentResolver().query(uri, null, null, null, null, null);

        if(data != null) {
            data.moveToFirst();
            Log.i("data test", data.getString(data.getColumnIndex("Name")));
        }

        String[] mListColumns = new String[] { gymContentProvider.COL_NAME, gymContentProvider.COL_DISTANCE};
        int[] mListItems = new int[] { R.id.gymNameTV, R.id.gymDistTV };

        adapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.workoutscard, data, mListColumns, mListItems );

        gymView.setAdapter(adapter);
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