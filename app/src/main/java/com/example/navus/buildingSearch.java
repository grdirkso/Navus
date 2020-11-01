package com.example.navus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class buildingSearch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_search);

        Spinner buildingPicker = (Spinner)  findViewById(R.id.buildingPicker);

        ArrayAdapter<String> buildings = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.buildings)
        );
        buildings.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        buildingPicker.setAdapter(buildings);
    }
}




// if (id = 0)
//    "https://maps.googleapis.com/maps/api/directions/json?origin=" + CurrentLocation + "&destination=" + Location0 + "&key=API_KEY"