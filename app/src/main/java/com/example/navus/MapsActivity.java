package com.example.navus;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public BuildingSearch buildingSearch;
    private String building;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        building = this.getIntent().getStringExtra("building");
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

