package com.example.golift.search;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.golift.R;
import com.example.golift.saved.BookmarkHelper;

public class gymsViewAdapter extends SimpleCursorAdapter {

    private Context context;
    private int layout;
    private Cursor cursor;
    private LayoutInflater inflater;

    public gymsViewAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to, 0);
        this.context = context;
        this.layout = layout;
        this.cursor = c;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
            holder = new ViewHolder();
            holder.nameTextView = convertView.findViewById(R.id.gymNameTV);
            holder.distanceTextView = convertView.findViewById(R.id.gymDistTV);
            holder.bookmarkButton = convertView.findViewById(R.id.bookmarkButton);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Move cursor to position
        if (cursor.moveToPosition(position)) {
            // Get gym data
            int idIndex = cursor.getColumnIndexOrThrow("_id");
            int nameIndex = cursor.getColumnIndexOrThrow(gymContentProvider.COL_NAME);
            int distanceIndex = cursor.getColumnIndexOrThrow(gymContentProvider.COL_DISTANCE);

            final long gymId = cursor.getLong(idIndex);
            String gymName = cursor.getString(nameIndex);
            int distance = cursor.getInt(distanceIndex);

            // Set gym data
            holder.nameTextView.setText(gymName);
            holder.distanceTextView.setText(distance + " km away");

            // Update bookmark button appearance
            updateBookmarkButton(holder.bookmarkButton, gymId);

            // Set bookmark button click listener
            holder.bookmarkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toggle bookmark
                    boolean isNowBookmarked = BookmarkHelper.toggleBookmark(context, gymId);

                    // Update button appearance
                    updateBookmarkButton((Button) v, gymId);
                }
            });
        }

        return convertView;
    }

    private void updateBookmarkButton(Button button, long gymId) {
        if (BookmarkHelper.isBookmarked(context, gymId)) {
            button.setText("★ Saved");
            button.setBackgroundTintList(context.getColorStateList(R.color.orange));
            button.setTextColor(context.getColor(R.color.white));
        } else {
            button.setText("☆ Save");
            button.setBackgroundTintList(context.getColorStateList(android.R.color.darker_gray));
            button.setTextColor(context.getColor(R.color.white));
        }
    }

    @Override
    public void changeCursor(Cursor cursor) {
        super.changeCursor(cursor);
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        TextView nameTextView;
        TextView distanceTextView;
        Button bookmarkButton;
    }
}