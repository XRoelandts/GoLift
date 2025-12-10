package com.example.golift.search;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton; // Use ImageButton
import android.widget.SimpleCursorAdapter;

import com.example.golift.R;


interface OnBookmarkButtonClickListener {
    void onBookmarkClick(Cursor cursor);
}

public class gymsViewAdapter extends SimpleCursorAdapter {

    private final OnBookmarkButtonClickListener listener;

    // FIX #1: REMOVED the extra constructor. This is now the ONLY one.
    public gymsViewAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags, OnBookmarkButtonClickListener listener) {
        super(context, layout, c, from, to, flags);
        this.listener = listener;
    }

    /**
     * This method is called for every row in the ListView.
     * We override it to find our button and attach a click listener.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Let the SimpleCursorAdapter do its job first to create/recycle the view
        // and populate the TextViews (gymNameTV, gymDistTV).
        View view = super.getView(position, convertView, parent);

        // FIX #2: Change the type from Button to ImageButton to match the XML.
        ImageButton bookmarkButton = view.findViewById(R.id.bookmarkButton);

        // Get the cursor pointing to the data for the current row
        Cursor cursor = (Cursor) getItem(position);

        if (bookmarkButton != null && listener != null) {
            bookmarkButton.setOnClickListener(v -> {
                // When the button is clicked, call the listener's method,
                // passing the cursor for this row back to the Activity.
                listener.onBookmarkClick(cursor);
            });
        }

        // Also removed the extra semicolon from the end of the method
        return view;
    }
}