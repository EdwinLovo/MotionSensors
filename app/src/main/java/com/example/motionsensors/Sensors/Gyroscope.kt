package com.example.motionsensors.Sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class Gyroscope(context: Context) {

    interface Listener{
        fun onRotation(rx:Float, ry:Float, rz:Float)
    }

    private lateinit var listener:Listener

    fun setListener(l:Listener){
        listener = l
    }

    private var sensorManager:SensorManager
    private var sensor:Sensor
    private var sensorEventListener:SensorEventListener

    init {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        sensorEventListener = object : SensorEventListener{
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onSensorChanged(event: SensorEvent?) {
                if (listener != null){
                    listener.onRotation(event!!.values[0],event.values[1],event.values[2])
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