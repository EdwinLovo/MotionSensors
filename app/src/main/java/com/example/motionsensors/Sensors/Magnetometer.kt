package com.example.motionsensors.Sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class Magnetometer(context: Context) {

    interface Listener{
        fun onTranslation(mx:Float, my:Float, mz:Float)
    }

    private lateinit var listener:Listener

    fun setListener(l:Listener){
        listener = l
    }

    private var sensorManager: SensorManager
    private var sensor: Sensor
    private var sensorEventListener: SensorEventListener

    init {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
        sensorEventListener = object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

            }

            override fun onSensorChanged(event: SensorEvent?) {
                if (listener != null){
                    listener.onTranslation(event!!.values[0],event.values[1],event.values[2])
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