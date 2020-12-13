package com.example.navus;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class LineView extends View {
    private Paint paint = new Paint();

    private ArrayList<ClassRoom> classPath;

    public LineView(Context context) {
        super(context);
    }

    public LineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        paint.setColor(Color.RED);

        paint.setStrokeWidth(20);

        int x;
        int y;
        double startX;
        double startY;
        double endX;
        double endY;
        for(int i = 0; i < classPath.size() - 1; i++) {

            x = 970;
            y = 40;

            startX = x-(classPath.get(i).getY()*1.381);
            startY= (classPath.get(i).getX()*1.8587) - y;
            endX = x-(classPath.get(i+1).getY()*1.381);
            endY= (classPath.get(i+1).getX()*1.8587) - y;
            Log.d("Path: ", startX + " " + startY + " " + endX + " " + endY);



            canvas.drawLine((float)startX, (float)startY, (float)endX, (float)endY, paint);
//

        }
        super.onDraw(canvas);
    }

    public void setPoints(ArrayList<ClassRoom> points)
    {
        classPath = points;
    }


    public void draw()
    {
        invalidate();
        requestLayout();
    }
}