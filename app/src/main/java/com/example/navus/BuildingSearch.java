package com.example.navus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

public class BuildingSearch extends AppCompatActivity {

    private Object item = new Object();
    private int selectedItem = 0;
    private Spinner classNum;
    private String building;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_search);

        final Spinner buildingPicker = (Spinner)  findViewById(R.id.buildingPicker);
        final ImageView buildingImg = (ImageView) findViewById(R.id.buildingImg);
        final Button getDirections = findViewById(R.id.getDirections);
        classNum = findViewById(R.id.classRoom);

        ArrayAdapter<String> buildings = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.buildings)
        );
        buildings.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        buildingPicker.setAdapter(buildings);

        buildingPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                item = adapterView.getItemAtPosition(i);

                if(item == adapterView.getItemAtPosition(0)) {
                    selectedItem = 0;
                    building = "bell";
                    buildingImg.setImageResource(R.drawable.bell);
                } else if(item == adapterView.getItemAtPosition(1)) {
                    selectedItem = 1;
                    building = "JHB";
                    buildingImg.setImageResource(R.drawable.jbht);
                } else if (item == adapterView.getItemAtPosition(2)){
                    selectedItem = 2;
                    building = "white";
                    buildingImg.setImageResource(R.drawable.sen);
                }

                getNumber();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        getDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(BuildingSearch.this, MapsActivity.class);
                intent.putExtra("building", selectedItem);
                startActivityForResult(intent, selectedItem);
            }
        });
    }

    public void getNumber() {
        if(building == "JBH") {
            ArrayAdapter<String> buildings = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1,
                    getResources().getStringArray(R.array.JBH)
            );
            buildings.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            classNum.setAdapter(buildings);
        }

        if(building == "white") {
            ArrayAdapter<String> buildings = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1,
                    getResources().getStringArray(R.array.white)
            );
            buildings.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            classNum.setAdapter(buildings);
        }

    }
}