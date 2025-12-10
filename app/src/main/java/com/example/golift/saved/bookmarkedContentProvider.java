package com.example.golift.saved;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

public class bookmarkedContentProvider extends ContentProvider {

    private MainDatabaseHelper mHelper;

    public final static String DBNAME = "bookmarkedGymDB";
    public final static String TABLE_NAME = "BookmarkedTable";

    // Store only the gym ID reference
    public static final String COL_GYM_ID = "gym_id";

    private final static String SQL_CREATE_MAIN =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    " _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_GYM_ID + " INTEGER UNIQUE)"; // UNIQUE ensures no duplicates

    public static final Uri CONTENT_URI = Uri.parse("content://com.example.golift.bookmarkedContentProvider");

    protected final class MainDatabaseHelper extends SQLiteOpenHelper {
        MainDatabaseHelper(Context context) {
            super(context, DBNAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_MAIN);
            Log.i("DB_BOOKMARKS", "Bookmarked table created");
        }

        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2){
            arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(arg0);
        }
    }

    public bookmarkedContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return mHelper.getWritableDatabase().delete(TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = mHelper.getWritableDatabase().insertWithOnConflict(
                TABLE_NAME,
                null,
                values,
                SQLiteDatabase.CONFLICT_IGNORE // Ignore if gym already bookmarked
        );

        if (id == -1) {
            Log.w("DB_BOOKMARKS", "Gym already bookmarked");
            return null;
        }

        return Uri.withAppendedPath(CONTENT_URI, "" + id);
    }

    @Override
    public boolean onCreate() {
        mHelper = new MainDatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return mHelper.getReadableDatabase().query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return mHelper.getWritableDatabase().update(TABLE_NAME, values, selection, selectionArgs);
    }

    // Helper method to check if a gym is bookmarked
    public boolean isGymBookmarked(Context context, long gymId) {
        Cursor cursor = context.getContentResolver().query(
                CONTENT_URI,
                new String[]{COL_GYM_ID},
                COL_GYM_ID + " = ?",
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
}