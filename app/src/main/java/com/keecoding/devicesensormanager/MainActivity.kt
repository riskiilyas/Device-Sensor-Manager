package com.keecoding.devicesensormanager

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.keecoding.devicesensormanager.databinding.ActivityMainBinding
import java.lang.NullPointerException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sensorManager: SensorManager
    private lateinit var accelerationSensor: Sensor
    private lateinit var gyroscopeSensor: Sensor
    private lateinit var temperatureSensor: Sensor
    private lateinit var magneticSensor: Sensor
    private lateinit var proximitySensor: Sensor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        binding.btnGPS.setOnClickListener {

        }

        binding.btnAcceleration.setOnClickListener {
            try {
                accelerationSensor =  sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!!
                val intentAccelerometer = Intent(this, AccelerometerActivity::class.java)
                startActivity(intentAccelerometer)
            } catch (e: NullPointerException) {
                showNotSupportedMessage()
            }
        }

        binding.btnGyro.setOnClickListener {
            try {
                gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)!!
                val intetGyro = Intent(this, GyroscopeActivity::class.java)
                startActivity(intetGyro)
            } catch (e: NullPointerException) {
                showNotSupportedMessage()
            }
        }

        binding.btnTemp.setOnClickListener {
            try {
                temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)!!
                val intentTemp = Intent(this, AmbientTemperatureActivity::class.java)
                startActivity(intentTemp)
            } catch (e: NullPointerException) {
                showNotSupportedMessage()
            }
        }

        binding.btnMagneticField.setOnClickListener {
            try {
                magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)!!
                val intentMagnetic = Intent(this, MagneticFieldActivity::class.java)
                startActivity(intentMagnetic)
            } catch (e: NullPointerException) {
                showNotSupportedMessage()
            }
        }

        binding.btnProximity.setOnClickListener {
            try {
                proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)!!
                val intentProximity = Intent(this, ProximityActivity::class.java)
                startActivity(intentProximity)
            } catch (e: NullPointerException) {
                showNotSupportedMessage()
            }
        }
    }

    private fun showNotSupportedMessage() {
        Toast.makeText(this, "This Sensor isn't supported!", Toast.LENGTH_SHORT).show()
    }
}