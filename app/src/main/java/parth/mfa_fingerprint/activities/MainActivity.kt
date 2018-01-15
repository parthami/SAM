package parth.mfa_fingerprint.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import parth.mfa_fingerprint.R
import parth.mfa_fingerprint.interfaces.MainView
import parth.mfa_fingerprint.types.AuthenticationNode

class MainActivity : Activity(), MainView {

    private val AUTHENTICATION_ONE_COMPLETED = 0
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Set text for factors
        val fingerprint = AuthenticationNode.FINGERPRINT
        factorOneText.text = fingerprint.label
    }

    override fun auth1Click(view: View) {
        val intent = Intent(this, FingerprintActivity::class.java)
        startActivityForResult(intent, AUTHENTICATION_ONE_COMPLETED)
    }

    fun logView(view: View) {
        val intent = Intent(this, AuthenticationLogActivity::class.java)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == AUTHENTICATION_ONE_COMPLETED && resultCode == Activity.RESULT_OK) {
            // Retrieve Authentication result
            val b = data.getBooleanExtra("result", false)
            //  Set the button based on the result
            auth1Button.isEnabled = !b
        }
    }

}
