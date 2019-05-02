package com.example.motionsensors

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.motionsensors.Sensors.Accelerometer
import com.example.motionsensors.Sensors.Gyroscope
import com.example.motionsensors.Sensors.Magnetometer
import com.example.motionsensors.Sensors.Proximity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var accelerometer: Accelerometer
    private lateinit var gyroscope: Gyroscope
    private lateinit var proximity: Proximity
    private lateinit var magnetometer: Magnetometer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        accelerometer = Accelerometer(this)
        gyroscope = Gyroscope(this)
        proximity = Proximity(this)
        magnetometer = Magnetometer(this)

        magnetometer.setListener(object: Magnetometer.Listener{
            override fun onTranslation(mx: Float, my: Float, mz: Float) {
                //Log.d("Mag", "mx" + mx.toString())
                //Log.d("Mag", "my" + my.toString())
                Log.d("Mag", "mz" + mz.toString())

                if(my > 9.6f){
                    textView3.text = "Dispositivo expuesto"
                }else if(my < -9.6f){
                    textView3.text = "Dispositivo volteado"
                }
            }
        })

        accelerometer.setListener( object : Accelerometer.Listener{
            override fun onTranslation(tx: Float, ty: Float, tz: Float) {
                if (tx > 1.0f){
                    window.decorView.setBackgroundColor(Color.CYAN)
                } else if (tx < -1.0f){
                    window.decorView.setBackgroundColor(Color.RED)
                }

            }
        })

        gyroscope.setListener(object : Gyroscope.Listener{
            override fun onRotation(rx: Float, ry: Float, rz: Float) {
                if (rz > 1.0f){
                    window.decorView.setBackgroundColor(Color.GREEN)
                } else if (rz < -1.0f){
                    window.decorView.setBackgroundColor(Color.YELLOW)
                }
            }
        })

        proximity.setListener(object : Proximity.Listener{
            override fun onProximity(cm: Float) {
                textView2.text = cm.toString()
                when (cm) {
                    in 0.0f..1.0f -> {
                        textView.text = "Muy Cerca"
                        textView.setTextColor(Color.RED)
                    }
                    in 1.1f..5.0f -> {
                        textView.text = "Cerca"
                        textView.setTextColor(Color.GREEN)
                    }
                    in 5.1f..9.0f -> {
                        textView.text = "Lejos"
                        textView.setTextColor(Color.MAGENTA)
                    }
                    in 9.1f..10.0f -> {
                        textView.text = "Muy Lejos"
                        textView.setTextColor(Color.BLUE)
                    }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()

        accelerometer.register()
        gyroscope.register()
        proximity.register()
        magnetometer.register()

    }

    override fun onPause() {
        super.onPause()

        accelerometer.unregister()
        gyroscope.unregister()
        proximity.unregister()
        magnetometer.unregister()

    }

}


