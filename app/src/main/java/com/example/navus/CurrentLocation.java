//package com.example.navus;
//
//import android.Manifest;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.util.Log;
//
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentActivity;
//
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.tasks.OnSuccessListener;
//
//public class CurrentLocation extends FragmentActivity implements OnMapReadyCallback {
//
//    private GoogleMap map;
//    private FusedLocationProviderClient client;
//    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
//
//    /**
//     * Will get the last known location
//     */
//    private void getLocationAndLog() {
//        if (client != null) {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                Log.d("MapsActivity", "No Location Permission");
//                return;
//            }
//            client.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
//                @Override
//                public void onSuccess(Location location) {
//                    if (location != null) {
//                        LatLng curLoc = new LatLng(location.getLatitude(), location.getLongitude());
//                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(curLoc,11));
//                    }
//                }
//            });
//        }
//    }
//    /**
//     * Enables the My Location layer if the fine location permission has been granted.
//     */
//    private void enableMyLocation() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            if (map != null) {
//                map.setMyLocationEnabled(true);
//            }
//        } else {
//            // Permission to access the location is missing. Show rationale and request permission
//            PermissionUtils.requestPermission(, LOCATION_PERMISSION_REQUEST_CODE,
//                    Manifest.permission.ACCESS_FINE_LOCATION, true);
//        }
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        if(googleMap != null) {
//            map = googleMap;
//            //phone can access location
//            enableMyLocation();
//            //get last known location
//            getLocationAndLog();
//        }
//    }
//}