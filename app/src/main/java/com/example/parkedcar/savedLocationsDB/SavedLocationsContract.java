package com.example.parkedcar.savedLocationsDB;

import android.provider.BaseColumns;

public class SavedLocationsContract {
    private SavedLocationsContract() {}

    public static class SavedLocations implements BaseColumns {
        public static final String TABLE_NAME = "savedLocations";
        public static final String COLUMN_SAVEDLOCATION_LATLNG = "latlng";
        public static final String COLUMN_SAVEDLOCATION_NAME = "name";
        public static final String COLUMN_BLOCKED = "isBlocked";
    }
}
