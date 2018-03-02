package parth.mfa_fingerprint.activities

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.CompoundButton
import kotlinx.android.synthetic.main.activity_sensor.*
import parth.mfa_fingerprint.R
import kotlin.math.round


class SensorActivity : AppCompatActivity(), SensorEventListener, CompoundButton.OnCheckedChangeListener{

    private var sensorManager : SensorManager? = null
    private var sensor : Sensor? = null
    var lightLevel : String = ""
    var motionLevel : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor)
        setSupportActionBar(findViewById(R.id.toolbar))

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
        sensorManager!!.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        toggleButton.setOnCheckedChangeListener(this)
        sensorInfo1.text = "Sensor activity loaded"
//        motionValue.text = motionLevel
//        lightValue.text = lightLevel
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onSensorChanged(event: SensorEvent?) {
        val sensor = event?.sensor
        if (sensor?.type == Sensor.TYPE_LIGHT) {
            val l0 = event.values?.get(0)
            lightLevel = "$l0 lux"
            lightValue.text = lightLevel
//            sensorInfo2.text = lightLevel
        } else if (sensor?.type == Sensor.TYPE_LINEAR_ACCELERATION) {
            val l0 = event.values?.get(0).toString().toFloat()
            val l1 = event.values?.get(1).toString().toFloat()
            val l2 = event.values?.get(2).toString().toFloat()
            val d = l0 * l0 + l1 * l1 + l2 * l2
            val acc = Math.sqrt(d.toDouble())
            val accerl = round(acc)
            motionLevel= "$accerl m/s^2"
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

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        if (isChecked) {
            sensorManager?.unregisterListener(this)
            sensor = sensorManager?.getDefaultSensor(Sensor.TYPE_LIGHT)
            sensorManager?.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
            sensorInfo1.text = "Sensor activity switched to light"
        } else {
            sensorManager?.unregisterListener(this)
            sensor = sensorManager?.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
            sensorManager?.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
            sensorInfo1.text = "Sensor activity switched to acceleration"
        }
    }

}
