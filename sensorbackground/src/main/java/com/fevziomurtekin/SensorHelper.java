package com.fevziomurtekin;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.LinkedList;

public class SensorHelper implements SensorEventListener {

    private SensorManager sensorManager;



    // The time in nanosecond when last sensor event happened.
    private long lastTimeSensor;
    // The radian the device already rotate along y-axis.
    private double rRadianY;
    // The radian the device already rotate along x-axis.
    private double rRadianX;

    private double rotateRadian = Math.PI/2;

    private LinkedList<SensorBackground> mViews = new LinkedList<>();


    public void create(Context context){

        // create sensor manager when checked sensor manager if null.
        if (sensorManager == null) {
            sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        }
        Sensor mSensor = null;

        //use accelerometer sensor.
        mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);

        lastTimeSensor = 0;
        rRadianY = rRadianY = 0;
    }

    public void delete (Context context){
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
            sensorManager = null;
        }
    }

    // add image in sensorBackground.
    void addsensorBackground(SensorBackground view) {
        if (view != null && !mViews.contains(view)) {
            mViews.addFirst(view);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (lastTimeSensor == 0) {
            lastTimeSensor = event.timestamp;
            return;
        }

        float rotateX = event.values[0];
        float rotateY = event.values[1];
        float rotateZ = event.values[2];

        for (SensorBackground view: mViews) {
            //treating 1g(9.8 m/s^2) = Math.PI/2,
            if(rotateX > 9.8f)
                rRadianX = 9.8f;
            else
                rRadianX = rotateX;

            if(rotateY > 9)
                rRadianX = 9;
            else
                rRadianY = rotateY;

            if (view != null && view.getOrientation() == SensorBackground.ORIENTATION_HORIZONTAL) {
                if(rotateX > (9.8f*(rotateX/1.57f)))
                    rRadianX = (9.8f*(float)(rotateRadian/1.57f));
                else if(rotateX < -(9.8f*(float)(rotateRadian/1.57f)))
                    rRadianX = -(9.8f*(float)(rotateRadian/1.57f));
                else
                    rRadianX = rotateX;
                float update = (float) ((rRadianX)/9.8f);
                view.updateProgress(update);
            }
            else if(view != null && view.getOrientation() == SensorBackground.ORIENTATION_VERTICAL){
                if(rotateY > (9.8f*(rotateRadian/1.57f)))
                    rRadianY = (9.8f*(float)(rotateRadian/1.57f));
                else if(rotateX < -(9.8f*(float)(rotateRadian/1.57f)))
                    rRadianY = -(9.8f*(float)(rotateRadian/1.57f));
                else
                    rotateRadian = rotateY;
                float update = (float) ((rRadianY)/9.8f);
                view.updateProgress(update);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}
