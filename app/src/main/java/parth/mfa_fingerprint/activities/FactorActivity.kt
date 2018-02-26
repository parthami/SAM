package parth.mfa_fingerprint.activities

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.transition.Slide
import android.util.Pair
import android.view.Gravity
import android.view.View
import kotlinx.android.synthetic.main.activity_factors.*
import parth.mfa_fingerprint.R
import parth.mfa_fingerprint.interfaces.MainView
import parth.mfa_fingerprint.types.AuthenticationNode

class FactorActivity : AppCompatActivity(), MainView {

    private val AUTHENTICATION_ONE_COMPLETED = 0
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_factors)
        setSupportActionBar(findViewById(R.id.toolbar))
        setupWindowAnimations()
        // Set text for factors
        val fingerprint = AuthenticationNode.FINGERPRINT
        val password = AuthenticationNode.PASSWORD
        // Set the factor labels
        factorOneText.text = fingerprint.label
        factorTwoText.text = password.label
    }

    override fun auth1Click(view: View) {
        // Load the fingerprint Activity
        val intent = Intent(this, FingerprintActivity::class.java)
        val options = ActivityOptions.makeSceneTransitionAnimation(this, factorOneText, "factorOneTextTrans")
        startActivity(intent, options.toBundle())
    }

    fun auth2Click(view: View) {
        val intent = Intent(this, PasswordActivity::class.java)
        startActivity(intent)
    }

    fun auth3Click(view: View) {
        val intent = Intent(this, QrActivity::class.java)
        val options = ActivityOptions.makeSceneTransitionAnimation(this,  Pair<View, String>(button3, "factorThreeButtonTrans"), Pair<View, String>(factorThreeText, "factorThreeTitleTrans"))
        startActivity(intent, options.toBundle())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == AUTHENTICATION_ONE_COMPLETED && resultCode == Activity.RESULT_OK) {
            // Retrieve Authentication result
            val b = data.getBooleanExtra("result", false)
            //  Set the button based on the result
            auth1Button.isEnabled = !b
        }
    }

    private fun setupWindowAnimations() {
        val slide = Slide()
        slide.duration = 100
        slide.slideEdge = Gravity.LEFT
        window.exitTransition = slide
    }

}
