package com.example.navus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BuildingSearch extends AppCompatActivity implements Callback<List<ClassRoom>>{

    private Object item = new Object();
    private Spinner classNum;
    private String room;
    private String building;
    BuildingAPI buildingAPI;
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
                    building = "JBH";
                    buildingImg.setImageResource(R.drawable.jbht);
                } else if (item == adapterView.getItemAtPosition(1)){
                    building = "white";
                    buildingImg.setImageResource(R.drawable.sen);
                }

                getNumber(building);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        getDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    sendClassRoom();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(BuildingSearch.this, MapsActivity.class);
                intent.putExtra("building", building);
                intent.putExtra("room", room);
                startActivity(intent);
            }
        });
    }

    public void getNumber(String building) {
        ArrayAdapter<String> classRooms = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.JBH)
        );

        classRooms.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classNum.setAdapter(classRooms);
        switch(building) {
            case "JBH" :
                classRooms = new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1,
                        getResources().getStringArray(R.array.JBH)
                );

                classRooms.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                classNum.setAdapter(classRooms);
                break;
            case "white" :
                classRooms = new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1,
                        getResources().getStringArray(R.array.white)
                );

                classRooms.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                classNum.setAdapter(classRooms);
                break;
        }


        classNum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                item = adapterView.getItemAtPosition(i);
                room = item.toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    static final String BASE_URL = "https://campus-nav.herokuapp.com/";

    public void sendClassRoom() throws IOException {
        try {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            buildingAPI = retrofit.create(BuildingAPI.class);
            sendPost();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void sendPost() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        BuildingAPI postAPI = retrofit.create(BuildingAPI.class);
        Call<List<ClassRoom>> call = postAPI.buildingDirections(building, Integer.valueOf(room));
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<ClassRoom>> call, Response<List<ClassRoom>> response) {
        if(response.isSuccessful()) {
            ArrayList<ClassRoom> myPostList = new ArrayList<ClassRoom>(response.body());
            for (ClassRoom post:myPostList) {
                Log.d("MainActivity","X: " + post.getX());
                Log.d("MainActivity","Y: " + post.getY());
                Log.d("MainActivity","PathID: " + post.getPathId());

            }

            Intent intent = new Intent(this, ClassPath.class);
            intent.putExtra("json", myPostList);
            intent.putExtra("building", building);
            startActivity(intent);
        } else {
            System.out.println(response.errorBody());
        }
        Debug.stopMethodTracing();
    }

    @Override
    public void onFailure(Call<List<ClassRoom>> call, Throwable t) {
        System.out.println("ERROR!!! Failed");
        t.printStackTrace();
    }
}
