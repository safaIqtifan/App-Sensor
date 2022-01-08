package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sm ;
    Sensor s;
    float x;
    TextView textView;
    TextView textView1;
    TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        textView = findViewById(R.id.textView);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        s = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if(s != null){
            Log.e(TAG, "Proximity sensor is found");
            sm.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);
        }else{
            Log.e(TAG, "Sensor not found");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {

            x = (int) event.values[0];
            float y = (int) event.values[1];
            float z = (int) event.values[2];
            textView.setText("x: " + x + "\ny: " + y + "\nz: " + z + "\nMax value: " + event.sensor.getMaximumRange());

            float xx =( (float)Math.abs(x/100.0));
            float yy =( (float)Math.abs(y/100.0));
            float zz =( (float)Math.abs(z/100.0));

            Toast.makeText(this, "xx: " + xx + "\nyy: " + yy + "\nzz: " + zz + "\nMax value: " + event.sensor.getMaximumRange(), Toast.LENGTH_SHORT).show();
            textView1.setText("xx: " + xx + "\nyy: " + yy + "\nzz: " + zz + "\nMax value: " + event.sensor.getMaximumRange());

            textView1.setAlpha(xx);
            textView2.setAlpha(yy);
            textView.setAlpha(zz);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(this,s, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }

    private int getTransparentColor(int color){
        int alpha = Color.alpha(color);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        // Set alpha based on your logic, here I'm making it 25% of it's initial value.
        alpha *= 0.25;

        return Color.argb(alpha, red, green, blue);
    }

}