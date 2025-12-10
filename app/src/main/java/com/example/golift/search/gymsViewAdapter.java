package com.example.golift.search;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton; // Use ImageButton
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.golift.R;




public class gymsViewAdapter extends SimpleCursorAdapter {



    // FIX #1: REMOVED the extra constructor. This is now the ONLY one.
    public gymsViewAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
        TextView gymNameTV = view.findViewById(R.id.gymNameTV);
        String gymName = gymNameTV.getText().toString();

        int position = cursor.getPosition();


        ImageButton bookmarkButton = view.findViewById(R.id.bookmarkButton);

        bookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("OnClick", gymName);
                if (context instanceof SearchActivity) {
                    Log.i("context", "correct");
                    ((SearchActivity) context).bookmarkGym(gymName, position);
                }

            }
        });

    }
}