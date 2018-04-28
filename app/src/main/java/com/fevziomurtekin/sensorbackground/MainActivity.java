package com.fevziomurtekin.sensorbackground;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.fevziomurtekin.SensorBackground;
import com.fevziomurtekin.SensorHelper;

public class MainActivity extends AppCompatActivity {

    private SensorBackground sensorBackground;
    private SensorHelper sensorHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorBackground=findViewById(R.id.sens);
        sensorHelper=new SensorHelper();
        sensorBackground.setSensorHelper(sensorHelper);
        sensorBackground.setSplash(true);
        sensorBackground.setSensorDelayTime(3000);
        sensorBackground.setSensorDelayTime(1000);
        sensorBackground.setSensorScrollListener(new SensorBackground.OnSensorScrollListener() {
            @Override
            public void onScrolled(SensorBackground view, float offsetProgress) {
                Log.e("Main","girdi");
            }
        });

        sensorBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,secondActivitity.class));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorHelper.create(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorHelper.delete(this);
    }
}
