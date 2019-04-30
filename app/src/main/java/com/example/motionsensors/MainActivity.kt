package com.example.motionsensors

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.motionsensors.Sensors.Accelerometer
import com.example.motionsensors.Sensors.Gyroscope

class MainActivity : AppCompatActivity() {

    private lateinit var accelerometer: Accelerometer
    private lateinit var gyroscope: Gyroscope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        accelerometer = Accelerometer(this)
        gyroscope = Gyroscope(this)

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
    }

    override fun onResume() {
        super.onResume()

        accelerometer.register()
        gyroscope.register()
    }

    override fun onPause() {
        super.onPause()

        accelerometer.unregister()
        gyroscope.unregister()
    }

}
