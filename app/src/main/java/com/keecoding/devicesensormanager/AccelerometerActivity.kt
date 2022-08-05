package com.keecoding.devicesensormanager

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.jjoe64.graphview.Viewport
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.keecoding.devicesensormanager.databinding.ActivityAccelerometerBinding
import kotlin.math.absoluteValue

class AccelerometerActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var binding: ActivityAccelerometerBinding
    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometerSensor: Sensor
    private lateinit var viewPort: Viewport
    private var currentAccelerationValue = 0.0
    private var previousAccelerationValue = 95.8
    private var pointsPlotted = 0
    private var graphIntervalCounter = 0
    private var series = LineGraphSeries<DataPoint>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccelerometerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding.btnBack.setOnClickListener { finish() }
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        viewPort = binding.graphView.viewport
        viewPort.isScrollable = true
        viewPort.isXAxisBoundsManual = true
        binding.graphView.addSeries(series)
    }

    @SuppressLint("SetTextI18n")
    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val x = it.values[0]
            val y = it.values[1]
            val z = it.values[2]

            currentAccelerationValue = ((x * x) + (y * y) + (z * z)).toDouble()
            val changedAccelerationValue = (currentAccelerationValue - previousAccelerationValue).absoluteValue
            previousAccelerationValue = currentAccelerationValue

            binding.tvPrevAcc.text = "Previous\t= $previousAccelerationValue"
            binding.tvCurrAcc.text = "Current\t\t= $currentAccelerationValue"
            binding.tvChangAcc.text = "Changed\t= $changedAccelerationValue"
            binding.progressBar.progress = changedAccelerationValue.toInt()

//            if(System.currentTimeMillis() - secondNow)
            pointsPlotted++
            series.appendData(DataPoint(pointsPlotted.toDouble(), changedAccelerationValue), true, pointsPlotted)
            viewPort.setMaxX(pointsPlotted.toDouble())
            viewPort.setMinX(pointsPlotted.toDouble()-200)
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