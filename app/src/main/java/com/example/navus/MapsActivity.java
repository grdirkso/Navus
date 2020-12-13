package com.example.navus;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public BuildingSearch buildingSearch;
    private String building;
    private FusedLocationProviderClient client;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mapFragment != null) {
            transaction.add(R.id.mapLayout, mapFragment);
            transaction.commit();
        }
        mapFragment.getMapAsync(this);
        building = this.getIntent().getStringExtra("building");
        final ArrayList<ClassRoom> myPostList = (ArrayList<ClassRoom>) this.getIntent().getSerializableExtra("json");
        Button hereBtn = findViewById(R.id.here);

        hereBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("maps: " , "here button clicked");
                Intent intent = new Intent(getApplicationContext(), ClassPath.class);
                intent.putExtra("json", myPostList);
                intent.putExtra("building", building);
                startActivity(intent);
            }
        });
    }

    /**
     * Will get the last known location
     */
    private void getLocationAndLog() {
        if (client != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.d("MapsActivity", "No Location Permission");
                return;
            }
            client.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        LatLng curLoc = new LatLng(location.getLatitude(), location.getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLoc,11));
                    }
                }
            });
        }
    }
    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //phone can access location
        enableMyLocation();
        //get last known location
        getLocationAndLog();

        switch(building) {
            case "JBH":
                System.out.println(building);
                // Add a marker at the J.B. Hunt building and move the camera
                LatLng jbhunt = new LatLng(36.06622685758442, -94.17374858900389);
                mMap.addMarker(new MarkerOptions().position(jbhunt).title("J.B. Hunt Center for Academic Excellence"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(jbhunt));
                break;
            case "bell":
                // Add a marker at Bell Engineering and move the camera
                LatLng bell = new LatLng(36.0671895009137, -94.17138824515493);
                mMap.addMarker(new MarkerOptions().position(bell).title("Bell Engineering Center"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(bell));
                break;
            case "white":
                // Add a marker at White Engineering Hall and move the camera
                LatLng white = new LatLng(36.06712139893772, -94.17055018102211);
                mMap.addMarker(new MarkerOptions().position(white).title("John White Jr. Engineering Hall"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(white));
                break;
            default:
                System.out.println("not working");
        }
    }
}

