package com.example.parkedcar;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentTransaction;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.parkedcar.savedLocationsDB.SavedLocationsContract;
import com.example.parkedcar.savedLocationsDB.SavedLocationsDBHelper;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity: ";

    private MapFragment mapFragment;
    private SQLiteDatabase db;
    private SavedLocationsDBHelper dbHelper;

    LatLng currentLocation;

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);

        Log.d(TAG, "onSavedInstanceState() called ");
        // Put current location Lat and Long into a string in onSaveInstanceState
        state.putSerializable("savedLocation", saveCurrentLocationString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null && savedInstanceState.getSerializable("savedLocation") != null) {
            Log.d(TAG, "savedInstanceState: "  + savedInstanceState.toString());
        }
        else {
            Log.d(TAG, "savedInstanceState is null");
        }

        // Set up database
        dbHelper = new SavedLocationsDBHelper(this);

        // Set up mapFragment
        mapFragment = new MapFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.map, mapFragment);
        fragmentTransaction.commit();

        ImageButton saveLocationBtn = findViewById(R.id.save_location_btn);
        saveLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Parking Location Saved", Snackbar.LENGTH_LONG);
                snackbar.show();

                currentLocation = mapFragment.saveCurrentLocation();
                saveCurrentLocationString();
            }
        });
    }

    private String saveCurrentLocationString() {
        String savedLocation = mapFragment.saveCurrentLocation().toString();
        Log.d(TAG, "currentLocation: " + currentLocation.toString());

        return savedLocation;
    }

    private long addLocationToDB(String latlng, String name) {
        ContentValues row = new ContentValues();

        row.put(SavedLocationsContract.SavedLocations.COLUMN_SAVEDLOCATION_LATLNG, latlng);
        row.put(SavedLocationsContract.SavedLocations.COLUMN_SAVEDLOCATION_NAME, name);

        db = dbHelper.getWritableDatabase();
        long status = db.insert(SavedLocationsContract.SavedLocations.TABLE_NAME, null, row);
        db.close();

        return status;
    }

    private long deleteLocationFromDB(String latlng) {
        if( latlng != null) {
            String sqlSelection = SavedLocationsContract.SavedLocations.COLUMN_SAVEDLOCATION_LATLNG + " = ?";
            String[] sqlSelectionArgs ={latlng};
            db = dbHelper.getWritableDatabase();
            long status = db.delete(SavedLocationsContract.SavedLocations.TABLE_NAME, sqlSelection, sqlSelectionArgs);
            db.close();
            return status;
        }
        else {
            Log.d(TAG, "Failed to remove saved location from the database ");
            return -1;
        }
    }
}
