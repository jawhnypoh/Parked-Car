package com.example.parkedcar;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentTransaction;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.parkedcar.savedLocationsDB.SavedLocationsDBHelper;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity: ";

    private MapFragment mapFragment;
    private SQLiteDatabase db;
    private SavedLocationsDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                Toast.makeText(MainActivity.this, "Parking Location Saved", Toast.LENGTH_SHORT).show();
                LatLng currentLocation = mapFragment.saveCurrentLocation();

                Log.d(TAG, "currentLocation: " + currentLocation.toString());
            }
        });
    }
}
