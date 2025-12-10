package com.example.golift.saved;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

/**
 * Helper class to manage gym bookmarks
 */
public class BookmarkHelper {

    /**
     * Add a gym to bookmarks
     * @param context Application context
     * @param gymId The ID of the gym from gymContentProvider
     * @return true if successfully added, false if already bookmarked
     */
    public static boolean addBookmark(Context context, long gymId) {
        // Check if already bookmarked
        if (isBookmarked(context, gymId)) {
            Toast.makeText(context, "Already in saved gyms!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Add to bookmarks
        ContentValues values = new ContentValues();
        values.put(bookmarkedContentProvider.COL_GYM_ID, gymId);

        Uri result = context.getContentResolver().insert(
                bookmarkedContentProvider.CONTENT_URI,
                values
        );

        if (result != null) {
            Toast.makeText(context, "Added to saved gyms!", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(context, "Failed to save gym", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * Remove a gym from bookmarks
     * @param context Application context
     * @param gymId The ID of the gym
     * @return true if successfully removed
     */
    public static boolean removeBookmark(Context context, long gymId) {
        int deletedRows = context.getContentResolver().delete(
                bookmarkedContentProvider.CONTENT_URI,
                bookmarkedContentProvider.COL_GYM_ID + " = ?",
                new String[]{String.valueOf(gymId)}
        );

        if (deletedRows > 0) {
            Toast.makeText(context, "Removed from saved gyms", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(context, "Failed to remove gym", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * Check if a gym is bookmarked
     * @param context Application context
     * @param gymId The ID of the gym
     * @return true if bookmarked
     */
    public static boolean isBookmarked(Context context, long gymId) {
        Cursor cursor = context.getContentResolver().query(
                bookmarkedContentProvider.CONTENT_URI,
                new String[]{bookmarkedContentProvider.COL_GYM_ID},
                bookmarkedContentProvider.COL_GYM_ID + " = ?",
                new String[]{String.valueOf(gymId)},
                null
        );

        boolean isBookmarked = false;
        if (cursor != null) {
            isBookmarked = cursor.getCount() > 0;
            cursor.close();
        }
        return isBookmarked;
    }

    /**
     * Toggle bookmark status
     * @param context Application context
     * @param gymId The ID of the gym
     * @return true if now bookmarked, false if now unbookmarked
     */
    public static boolean toggleBookmark(Context context, long gymId) {
        if (isBookmarked(context, gymId)) {
            removeBookmark(context, gymId);
            return false;
        } else {
            addBookmark(context, gymId);
            return true;
        }
    }
}