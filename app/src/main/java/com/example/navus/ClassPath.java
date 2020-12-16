package com.example.navus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

public class ClassPath extends AppCompatActivity {

    private LineView mLineView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_path);

        Button backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BuildingSearch.class);
                startActivity(intent);
            }
        });

        ArrayList<ClassRoom> path = new ArrayList<ClassRoom>();
        path = (ArrayList<ClassRoom>) this.getIntent().getSerializableExtra("json");


        mLineView = (LineView) findViewById(R.id.lineView);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            switch(this.getIntent().getStringExtra("building")) {
                case "JBH":
                    mLineView.setBackground(getResources().getDrawable(R.drawable.jbhuntfloor1));
                    break;
            }


        }
        mLineView.setPoints(path);





    }

}