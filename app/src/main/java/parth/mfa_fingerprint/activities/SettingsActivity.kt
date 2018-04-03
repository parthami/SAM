package parth.mfa_fingerprint.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import parth.mfa_fingerprint.R


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(findViewById(R.id.toolbar))
    }
}
