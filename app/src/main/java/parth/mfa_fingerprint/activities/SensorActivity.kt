package parth.mfa_fingerprint.activities

import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.CompoundButton
import kotlinx.android.synthetic.main.activity_sensor.*
import parth.mfa_fingerprint.R


class SensorActivity : Activity(), SensorEventListener, CompoundButton.OnCheckedChangeListener{

    private var sensorManager : SensorManager? = null
    private var sensor : Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
        sensorManager!!.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        toggleButton.setOnCheckedChangeListener(this)
        sensorInfo1.text = "Sensor activity loaded"
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onSensorChanged(event: SensorEvent?) {
        val sensor = event?.sensor
        if (sensor?.type == Sensor.TYPE_LIGHT) {
            val l0 = event.values?.get(0)
            sensorInfo2.text = "Light level : $l0"
        } else if (sensor?.type == Sensor.TYPE_LINEAR_ACCELERATION) {
            val l0 = event.values?.get(0)
            val l1 = event.values?.get(1)
            val l2 = event.values?.get(2)
            sensorInfo2.text = "Motion detected \n $l0 \n $l1 \n $l2"
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
