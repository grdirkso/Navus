package com.example.navus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

import java.util.ArrayList;

public class ClassPath extends AppCompatActivity {

    private LineView mLineView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_path);

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