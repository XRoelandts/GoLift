package com.example.golift.saved;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import com.example.golift.search.gymContentProvider;

public class bookmarkedContentProvider extends ContentProvider {


    private MainDatabaseHelper mHelper;

    public final static String DBNAME = "gymDB";

    public final static String TABLE_NAME = "TaskTable";


    public static final String COL_NAME = "Name";
    public static final String COL_DISTANCE = "Distance";
    public static final String COL_DESCRIPTION = "Description";



    private final static String SQL_CREATE_MAIN =
            "CREATE TABLE TaskTable (" +
                    " _id INTEGER PRIMARY KEY, " +
                    COL_NAME + " TEXT, " +
                    COL_DISTANCE + " INTEGER, " +
                    COL_DESCRIPTION + " TEXT)";

    public static final Uri CONTENT_URI = Uri.parse("content://com.example.golift.bookmarkedContentProvider");

    protected final class MainDatabaseHelper extends SQLiteOpenHelper {
        MainDatabaseHelper(Context context) {
            super(context, DBNAME, null, 5);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_MAIN);

            // Verify columns
            Cursor cursor = db.rawQuery("PRAGMA table_info(" + TABLE_NAME + ")", null);
            while (cursor.moveToNext()) {
                String columnName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                Log.i("DB_SCHEMA", "Column: " + columnName);
            }
            cursor.close();

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
        // Implement this to handle requests to delete one or more rows.
        return mHelper.getWritableDatabase().
                delete(TABLE_NAME, selection,
                        selectionArgs);
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        String Name = values.getAsString(COL_NAME);
        String Distance = values.getAsString(COL_DISTANCE);
        String Description = values.getAsString(COL_DESCRIPTION);


        long id = mHelper.getWritableDatabase().insert(TABLE_NAME, null, values);

        return Uri.withAppendedPath(CONTENT_URI, "" + id);
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        mHelper = new MainDatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        return mHelper.getReadableDatabase().query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        return mHelper.getWritableDatabase().
                update(TABLE_NAME, values, selection,
                        selectionArgs);
    }
}