package parth.mfa_fingerprint.fragments

import android.os.Bundle
import android.preference.PreferenceFragment
import parth.mfa_fingerprint.R


/**
 * Created by Parth Chandratreya on 01/03/2018.
 */

class SettingsFragment : PreferenceFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preferences)
    }
}