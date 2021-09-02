package com.futured.app.android.ui.screens

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getSystemService
import kotlin.math.sqrt


@Composable
fun CoinsScreen(viewModel: CoinsViewModel = CoinsViewModel()) {



    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        initSensor(viewModel)
        Text(text = "SHAKE", color = viewModel.color.value, fontSize = viewModel.size.value)
    }

}

@Composable
fun initSensor(viewModel: CoinsViewModel) {

    val ctx = LocalContext.current
    val mSensorManager = getSystemService(LocalContext.current, SensorManager::class.java)
    val mAccelerometer = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    val mShakeDetector = ShakeDetector()
    mShakeDetector.setOnShakeListener(object : ShakeDetector.OnShakeListener {
        override fun onShake(count: Int) {
            viewModel.fetchCoins()
        }
    })

    remember {
        mSensorManager.registerListener(
            mShakeDetector,
            mAccelerometer,
            SensorManager.SENSOR_DELAY_UI
        )
    }
}

class ShakeDetector : SensorEventListener {
    private var mListener: OnShakeListener? = null
    private var mShakeTimestamp: Long = 0
    private var mShakeCount = 0
    fun setOnShakeListener(listener: OnShakeListener?) {
        mListener = listener
    }

    interface OnShakeListener {
        fun onShake(count: Int)
    }

    override fun onAccuracyChanged(
        sensor: Sensor,
        accuracy: Int
    ) { // ignore
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (mListener != null) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            val gX = x / SensorManager.GRAVITY_EARTH
            val gY = y / SensorManager.GRAVITY_EARTH
            val gZ = z / SensorManager.GRAVITY_EARTH
            // gForce will be close to 1 when there is no movement.
            val gForce: Float = sqrt(gX * gX + gY * gY + gZ * gZ)
            if (gForce > SHAKE_THRESHOLD_GRAVITY) {
                val now = System.currentTimeMillis()
                // ignore shake events too close to each other (500ms)
                if (mShakeTimestamp + SHAKE_SLOP_TIME_MS > now) {
                    return
                }
                // reset the shake count after 3 seconds of no shakes
                if (mShakeTimestamp + SHAKE_COUNT_RESET_TIME_MS < now) {
                    mShakeCount = 0
                }
                mShakeTimestamp = now
                mShakeCount++
                mListener!!.onShake(mShakeCount)
            }
        }
    }

    companion object {
        /*
     * The gForce that is necessary to register as shake.
     * Must be greater than 1G (one earth gravity unit).
     * You can install "G-Force", by Blake La Pierre
     * from the Google Play Store and run it to see how
     *  many G's it takes to register a shake
     */
        private const val SHAKE_THRESHOLD_GRAVITY = 2.7f
        private const val SHAKE_SLOP_TIME_MS = 500
        private const val SHAKE_COUNT_RESET_TIME_MS = 3000
    }
}
