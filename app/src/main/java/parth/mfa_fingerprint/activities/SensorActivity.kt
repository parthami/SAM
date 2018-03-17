package parth.mfa_fingerprint.activities

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_sensor.*
import parth.mfa_fingerprint.R
import parth.mfa_fingerprint.helpers.SoundMeter
import kotlin.math.round


class SensorActivity : AppCompatActivity(), SensorEventListener {

    private var sensorManager: SensorManager? = null
    private var sensor: Sensor? = null

    var rawLightLevel: Double = 0.0
    var rawMotionLevel: Double = 0.0
    var rawSoundLevel: Double = 0.0

    var lightLevel: String = ""
    var motionLevel: String = ""
    var soundLevel: String = ""

    val mHandler = Handler()
    val soundMeter = SoundMeter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor)
        setSupportActionBar(findViewById(R.id.toolbar))

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)
        sensorManager!!.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)

        sensorInfo1.text = "Sensor activity loaded"
//        motionValue.text = motionLevel
//        lightValue.text = lightLevel
        lightValue.addTextChangedListener(textWatcher)

        soundMeter.start()
        rawSoundLevel = soundMeter.amplitude
        Log.i("PTAG", "Checking sound first: $rawSoundLevel")
        mHandler.postDelayed(mPollTask, 1000)
//        soundValue.addTextChangedListener(textWatcher2)
    }

    private val mPollTask = object : Runnable {
        override fun run() {
            rawSoundLevel = soundMeter.amplitude
            Log.i("PTAG", "Checking sound: $rawSoundLevel")
            soundLevel = "$rawSoundLevel %"
            if (rawSoundLevel == 0.0) {
                mHandler.postDelayed(this, 1000)
            } else {
                soundValue.text = soundLevel
                Log.i("PTAG", "SOUND LEVEL DONE")
                stop()
            }
        }
    }

    fun stop() {
        mHandler.removeCallbacks(mPollTask)
        soundMeter.stop()
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            Log.i("PTAG", "MOTION SENSOR INFO CHANGED")
            switchToMotion()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onSensorChanged(event: SensorEvent?) {
        val sensor = event?.sensor
        if (sensor?.type == Sensor.TYPE_LIGHT) {
            rawLightLevel = event.values?.get(0)!!.toDouble()
            lightLevel = "$rawLightLevel lux"
            lightValue.text = lightLevel
        } else if (sensor?.type == Sensor.TYPE_LINEAR_ACCELERATION) {
            val l0 = event.values?.get(0).toString().toFloat()
            val l1 = event.values?.get(1).toString().toFloat()
            val l2 = event.values?.get(2).toString().toFloat()
            val d = l0 * l0 + l1 * l1 + l2 * l2
            val acc = Math.sqrt(d.toDouble())
            rawMotionLevel = round(acc)
            motionLevel = "$rawMotionLevel m/s^2"
            motionValue.text = motionLevel
        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager?.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)
    }

    fun switchToMotion() {
        sensorManager?.unregisterListener(this)
        sensor = sensorManager?.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
        sensorManager?.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        sensorInfo1.text = "Sensor activity switched to motion"
    }

    fun sendToSelection(v: View) {
        val array : DoubleArray = doubleArrayOf(rawLightLevel, rawMotionLevel, rawSoundLevel)
        val intent = Intent(this, SelectionActivity::class.java)
        intent.putExtra("levels", array)
        startActivity(intent)
    }

}
