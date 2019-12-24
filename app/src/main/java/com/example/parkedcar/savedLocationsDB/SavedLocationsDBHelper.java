package com.example.parkedcar.savedLocationsDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SavedLocationsDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "savedPastLocations.db";
    private static final int DATABASE_VERSION = 1;

    public SavedLocationsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_FOLLOWING_SAVEDLOCATIONS_TABLE =
                "CREATE TABLE " + SavedLocationsContract.SavedLocations.TABLE_NAME + "(" +
                        SavedLocationsContract.SavedLocations._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        SavedLocationsContract.SavedLocations.COLUMN_SAVEDLOCATION_LATLNG + " TEXT NOT NULL, " +
                        SavedLocationsContract.SavedLocations.COLUMN_SAVEDLOCATION_NAME + " TEXT, " +
                        ");";

        db.execSQL(SQL_CREATE_FOLLOWING_SAVEDLOCATIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SavedLocationsContract.SavedLocations.TABLE_NAME);
        onCreate(db);
    }
}
