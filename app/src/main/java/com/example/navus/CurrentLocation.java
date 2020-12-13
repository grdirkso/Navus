package com.example.navus;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.

public class CurrentLocation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private FusedLocationProviderClient client;
    
    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(googleMap != null) {
            map = googleMap;
        }
    }
}