package com.keecoding.devicesensormanager

import android.app.Application
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.lang.NullPointerException

class MainActivity : AppCompatActivity() {
    private lateinit var sensorManager: SensorManager
    private lateinit var accelerationSensor: Sensor
    private lateinit var gyroscopeSensor: Sensor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        try {
            accelerationSensor =  sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!!
        } catch (e: NullPointerException) {

        }

        try {
            gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)!!
        } catch (e: NullPointerException) {

        }
    }
}