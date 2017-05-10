package com.cc.rubick;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class SensorActivity extends AppCompatActivity implements SensorEventListener {
    private TextView ambientTemperatureTv;
    private TextView lightTV;
    private TextView pressureTV;
    private TextView stepDetectorTV;
    private TextView doubleTwistTV;//湿度传感器
    private TextView relativeHumidityTV;//湿度传感器

    private int a;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);


        //获取光线传感器
        Sensor lightSensor = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        sm.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        lightTV = (TextView) findViewById(R.id.lightTV);
        //获取温度传感器
        Sensor ambientTemperatureSensor = sm.getDefaultSensor(65536);
        sm.registerListener(this, ambientTemperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        ambientTemperatureTv = (TextView) findViewById(R.id.temperatureTV);
        //压力传感器
        Sensor pressureSensor = sm.getDefaultSensor(Sensor.TYPE_PRESSURE);
        sm.registerListener(this, pressureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        pressureTV = (TextView) findViewById(R.id.pressureTV);
        //步数传感器
        Sensor type_step_detector = sm.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        sm.registerListener(this, type_step_detector, SensorManager.SENSOR_DELAY_NORMAL);
        stepDetectorTV = (TextView) findViewById(R.id.stepDetectorTV);
        //Pickup Gesture
        Sensor relative_humidity = sm.getDefaultSensor(Sensor.TYPE_GRAVITY);
        sm.registerListener(this, relative_humidity, SensorManager.SENSOR_DELAY_NORMAL);
        relativeHumidityTV = (TextView) findViewById(R.id.relativeHumidityTV);
        //双扭
        //TYPE_RELATIVE_HUMIDITY 双扭
        Sensor Double_twist = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sm.registerListener(this, Double_twist, SensorManager.SENSOR_DELAY_NORMAL);
        doubleTwistTV = (TextView) findViewById(R.id.doubleTwistTV);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        //Log.v("event.sensor.getType()", event.sensor.getType() + "");

        float data = event.values[0];

        if (event.sensor.getType() == 65536) {//环境温度
            ambientTemperatureTv.setText("温度:" + data + "°C");
        } else if (event.sensor.getType() == Sensor.TYPE_LIGHT) {//灯光强度
            lightTV.setText("光线：" + data);
        } else if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
            pressureTV.setText("压力:" + data + "");
        } else if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            int b = a++;
            stepDetectorTV.setText("步数:" + b + "");
        } else if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {
            relativeHumidityTV.setText("重力加速度:" + data);
        } else if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            doubleTwistTV.setText("距离:" + data);
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {


    }
}
