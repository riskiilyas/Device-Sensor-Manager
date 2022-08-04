package com.keecoding.devicesensormanager

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.keecoding.devicesensormanager.databinding.ActivityAccelerometerBinding
import kotlin.math.absoluteValue

class AccelerometerActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var binding: ActivityAccelerometerBinding
    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometerSensor: Sensor
    private var currentAccelerationValue = 0.0
    private var previousAccelerationValue = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccelerometerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val x = it.values[0]
            val y = it.values[1]
            val z = it.values[2]

            currentAccelerationValue = ((x * x) + (y * y) + (z * z)).toDouble()
            val changedAccelerationValue = (currentAccelerationValue - previousAccelerationValue).absoluteValue
            previousAccelerationValue = currentAccelerationValue

            binding.tvPrevAcc.text = previousAccelerationValue.toString()
            binding.tvCurrAcc.text = currentAccelerationValue.toString()
            binding.tvChangAcc.text = changedAccelerationValue.toString()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onResume() {
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL)
        super.onResume()
    }

    override fun onPause() {
        sensorManager.unregisterListener(this)
        super.onPause()
    }
}