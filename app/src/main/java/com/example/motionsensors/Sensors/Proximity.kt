package com.example.motionsensors.Sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class Proximity (context: Context) {

    interface Listener{
        fun onProximity (cm : Float)
    }

    private lateinit var listener:Listener

    fun setListener (l:Listener){
        listener = l
    }

    private var sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private var sensor: Sensor
    private var sensorEventListener: SensorEventListener

    init{
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        sensorEventListener = object : SensorEventListener{
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            }
            override fun onSensorChanged(event: SensorEvent?) {
                if (listener != null){
                    listener.onProximity(event!!.values[0])
                }
            }

        }
    }

    fun register(){
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    fun unregister(){
        sensorManager.unregisterListener(sensorEventListener)
    }
}