package com.example.navus;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.android.PolyUtil;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public BuildingSearch buildingSearch;
    private String building;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private LatLng curLoc;
    private LatLng geoDestination;
    private FusedLocationProviderClient mFusedLocationClient;
    private Double lat;
    private Double lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
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
        Button directionsBtn = findViewById(R.id.mapsBtn);

        hereBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("maps: ", "here button clicked");
                Intent intent = new Intent(getApplicationContext(), ClassPath.class);
                intent.putExtra("json", myPostList);
                intent.putExtra("building", building);
                startActivity(intent);
            }
        });

        directionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBuilding();
                Log.d("maps: ",geoDestination.latitude + "," + geoDestination.longitude );
                String geo =  "https://maps.google.com/maps?daddr="+geoDestination.latitude + "," + geoDestination.longitude;
                Uri gmmIntentUri = Uri.parse(geo);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if(mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }

            }
        });

    }


    /**
     * Will get the last known location
     */
    private void getLocationAndLog() {
        if (mFusedLocationClient != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.d("MapsActivity", "No Location Permission");
                return;
            }
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        lat = location.getLatitude();
                        lng = location.getLongitude();
                        LatLng curLoc = new LatLng(location.getLatitude(), location.getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLoc, 15));
                        directions();
                    }
                }
            });
        }
        //load all picture items from db
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

        getBuilding();

    }


    public void getBuilding() {
        switch (building) {
            case "JBH":
                // Add a marker at the J.B. Hunt building and move the camera
                LatLng jbhunt = new LatLng(36.06622685758442, -94.17374858900389);
                mMap.addMarker(new MarkerOptions().position(jbhunt).title("J.B. Hunt Center for Academic Excellence"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(jbhunt));
                geoDestination = jbhunt;
                break;
            case "bell":
                // Add a marker at Bell Engineering and move the camera
                LatLng bell = new LatLng(36.0671895009137, -94.17138824515493);
                mMap.addMarker(new MarkerOptions().position(bell).title("Bell Engineering Center"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(bell));
                geoDestination = bell;
                break;
            case "white":
                // Add a marker at White Engineering Hall and move the camera
                LatLng white = new LatLng(36.06712139893772, -94.17055018102211);
                mMap.addMarker(new MarkerOptions().position(white).title("John White Jr. Engineering Hall"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(white));
                geoDestination = white;
                break;
            default:
                System.out.println("not working");
        }
    }

    public void directions() {

        Log.d("Maps: ", "directons");
        Instant now = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            now = Instant.now();
        }
        String origin = getCompleteAddressString(lat, lng);
        String destination = getCompleteAddressString(geoDestination.latitude, geoDestination.longitude);
        try {
            DirectionsResult result = DirectionsApi.newRequest(getGeoContext()).mode(TravelMode.DRIVING).origin(origin)
                    .destination(destination)
                    .departureTime(now).await();
            addPolyline(result, mMap);
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private GeoApiContext getGeoContext() throws InterruptedException, ApiException, IOException {
        return new GeoApiContext.Builder()
                .queryRateLimit(1)
                .apiKey("AIzaSyC_moMjS9Bjbzsn7MwNBhIfT0kS33a5r-w")
                .connectTimeout(1, TimeUnit.SECONDS)
                .readTimeout(1, TimeUnit.SECONDS)
                .writeTimeout(1, TimeUnit.SECONDS)
                .build();
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("", strReturnedAddress.toString());
            } else {
                Log.w("", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("", "Canont get Address!");
        }
        return strAdd;

    }

    public void directions(double lat, double lng){

    }

    private void addPolyline(DirectionsResult results, GoogleMap mMap) {
        List<LatLng> decodedPath = PolyUtil.decode(results.routes[0].overviewPolyline.getEncodedPath());
        mMap.addPolyline(new PolylineOptions().addAll(decodedPath));
    }
}

