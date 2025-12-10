package com.example.golift.search;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.example.golift.saved.bookmarkedContentProvider;

public class SearchActivity extends AppCompatActivity {

    FragmentManager fg;

    gymContentProvider gymProvider;
    SimpleCursorAdapter adapter;
    gymsViewAdapter adapter1;

    bookmarkedContentProvider bookmarkProvider;

    Uri uri = gymContentProvider.CONTENT_URI;

    Uri bookmarkUri = bookmarkProvider.CONTENT_URI;

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

        bookmarkProvider = new bookmarkedContentProvider();

        // Search setup
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
                adapter1.changeCursor(filteredCursor);

                return true; // We've handled the event
            }

            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }
        });

        // List View setup

        gymView = findViewById(R.id.gymLV);

        Cursor data = getContentResolver().query(uri, null, null, null, null, null);

        if(data != null) {
            data.moveToFirst();
            Log.i("data test", data.getString(data.getColumnIndex("Name")));
        }

        String[] mListColumns = new String[] { gymContentProvider.COL_NAME, gymContentProvider.COL_DISTANCE};
        int[] mListItems = new int[] { R.id.gymNameTV, R.id.gymDistTV };

        adapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.workoutscard, data, mListColumns, mListItems );
        adapter1 = new gymsViewAdapter(SearchActivity.this, R.layout.workoutscard, data, mListColumns, mListItems);

        gymView.setAdapter(adapter1);
    }

    public void bookmarkGym(String gymName, int position) {

        Log.i("bookmark", "check");

        Cursor data = getContentResolver().query(uri, null, null,
                null, null, null);

        Cursor data1 = getContentResolver().query(bookmarkUri, null, null,
                null, null, null);

        data.moveToPosition(position);

        // Check if something is already bookmarked
        Boolean go = true;

        if(data1 != null) {
            for (int i = 0; i < data1.getCount(); i++) {
                data1.moveToPosition(i);
                if (gymName.equals(data1.getString(data.getColumnIndex("Name")))) {
                    go = false;
                }
            }
        }

        if(go) {
            ContentValues values = new ContentValues();
            values.put(bookmarkedContentProvider.COL_NAME, data.getString(data.getColumnIndex("Name")));
            values.put(bookmarkedContentProvider.COL_DISTANCE, data.getString(data.getColumnIndex("Distance")));
            values.put(bookmarkedContentProvider.COL_DESCRIPTION, data.getString(data.getColumnIndex("Description")));

            // Use the ContentResolver to add the row.
            getContentResolver().insert(bookmarkUri, values);

            for(int i = 0; i < data1.getCount(); i++)
            {
                data1.moveToPosition(i);
                Log.i("BookmarkDB", data1.getString(data.getColumnIndex("Name")));
            }
        } else {
            Toast.makeText(SearchActivity.this, "Gym already bookmarked", Toast.LENGTH_SHORT).show();
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