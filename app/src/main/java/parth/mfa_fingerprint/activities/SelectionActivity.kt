package parth.mfa_fingerprint.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_selection.*
import parth.mfa_fingerprint.R
import parth.mfa_fingerprint.helpers.AliasMethod
import parth.mfa_fingerprint.types.AuthenticationNode
import parth.mfa_fingerprint.types.Enviroment


class SelectionActivity : AppCompatActivity() {

    var SOUND_LOW_LIMIT = 0
    var SOUND_HIGH_LIMIT = 100
    var LIGHT_LOW_LIMIT = 0
    var LIGHT_HIGH_LIMIT = 100
    var MOTION_LOW_LIMIT = 0
    var MOTION_HIGH_LIMIT = 100
    var lightLevel: Double = 0.0
    var motionLevel: Double = 0.0
    var soundLevel: Double = 0.0
    val allFactorList = AuthenticationNode.values()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)
        setSupportActionBar(findViewById(R.id.toolbar))

        authButton.isEnabled = false
        authButton.setBackgroundColor(Color.GRAY)

        val passwordPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        LIGHT_LOW_LIMIT = passwordPreferences.getString("pref_key_light_lower", "0").toInt()
        LIGHT_HIGH_LIMIT = passwordPreferences.getString("pref_key_light_higher", "100").toInt()
        SOUND_LOW_LIMIT = passwordPreferences.getString("pref_key_sound_lower", "0").toInt()
        SOUND_HIGH_LIMIT = passwordPreferences.getString("pref_key_sound_higher", "100").toInt()
        MOTION_LOW_LIMIT = passwordPreferences.getString("pref_key_motion_lower", "0").toInt()
        MOTION_HIGH_LIMIT = passwordPreferences.getString("pref_key_motion_higher", "100").toInt()

        selection()
    }

    private fun selection() {
        Log.i("PTAG", "Sound limits $SOUND_LOW_LIMIT / $SOUND_HIGH_LIMIT")
        Log.i("PTAG", "Light limits $LIGHT_LOW_LIMIT / $LIGHT_HIGH_LIMIT")
        Log.i("PTAG", "Motion limits $MOTION_LOW_LIMIT / $MOTION_HIGH_LIMIT")
        // Get Sensor values
        val value = intent.getDoubleArrayExtra("levels")
        lightLevel = value[0]
        motionLevel = value[1]
        soundLevel = value[2]
        Log.i("PTAG", "Sensor values: $lightLevel $motionLevel $soundLevel")
        // Calculate if values are in range
        val lightInRange = lightLevel in (LIGHT_LOW_LIMIT)..(LIGHT_HIGH_LIMIT)
        val motionInRange = motionLevel in (MOTION_LOW_LIMIT)..(MOTION_HIGH_LIMIT)
        val soundInRange = soundLevel in (SOUND_LOW_LIMIT)..(SOUND_HIGH_LIMIT)
        Log.i("PTAG", "Range values: $lightInRange $motionInRange $soundInRange")
        // Disable factors

        if (!lightInRange) {
            allFactorList.filter { it.affectedBy == Enviroment.LIGHT }.map { it.enabled = false }
            Log.i("PTAG", "LIGHT disabled")
        }
        if (!motionInRange) {
            allFactorList.filter { it.affectedBy == Enviroment.MOTION }.map { it.enabled = false }
            Log.i("PTAG", "MOTION disabled")
        }
        if (!soundInRange) {
            allFactorList.filter { it.affectedBy == Enviroment.SOUND }.map { it.enabled = false }
            Log.i("PTAG", "SOUND disabled")
        }
        // Convert to arraylist of strings
        var enableCounter = 0
        for (i in 0..allFactorList.lastIndex) {
            val textView = TextView(this)
            textView.text = allFactorList[i].label
            textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
            textView.textSize = 30.0f
            if (!allFactorList[i].enabled) {
                textView.setTextColor(Color.RED)
                disabledFactors.addView(textView)
            } else {
                textView.setTextColor(ContextCompat.getColor(this, R.color.secondaryColor))
                enabledFactors.addView(textView)
                enableCounter++
            }
        }
        if (enableCounter > 2) {
            authButton.isEnabled = true
            authButton.setBackgroundColor(ContextCompat.getColor(this, R.color.secondaryColor))
        }
        // Send the remaining enabled factors to the list as Strings

    }

    fun sendToList(v: View) {
        val intent = Intent(this, FactorActivity::class.java)
        val enabledFactors : ArrayList<AuthenticationNode> = ArrayList(0)
        val probabilities : ArrayList<Double> = ArrayList(0)
        val factors : ArrayList<String>
        // Filter labels and probabilities
        allFactorList.filter { it.enabled }.map { enabledFactors.add(it) }
        enabledFactors.filter { probabilities.add(it.probability) }
        factors = getThreeFactors(enabledFactors,probabilities)
        // Send to intent
        intent.putStringArrayListExtra("factors", factors)
        Log.i("PTAG", "Sending $factors")
        startActivity(intent)
    }

    fun getThreeFactors(factors :  ArrayList<AuthenticationNode>, probabilities: ArrayList<Double> ) : ArrayList<String> {
        val chosenFactors : ArrayList<String> = ArrayList(3)
        // Factor 1
        var alias = AliasMethod(probabilities)
        val f1 = alias.generation()
        chosenFactors.add(factors[f1].label.toUpperCase())
        probabilities.removeAt(f1)
        factors.removeAt(f1)
        // Factor 2
        alias = AliasMethod(probabilities)
        val f2 = alias.generation()
        chosenFactors.add(factors[f2].label.toUpperCase())
        probabilities.removeAt(f2)
        factors.removeAt(f2)
        // Factor 3
        alias = AliasMethod(probabilities)
        val f3 = alias.generation()
        chosenFactors.add(factors[f3].label.toUpperCase())
        return chosenFactors
    }

    private fun factorNamesAsString(array: Array<AuthenticationNode>): ArrayList<String> {
        val g: ArrayList<String> = ArrayList(0)
        array.forEach {
            g.add(it.toString())
        }
        return g
    }
}
