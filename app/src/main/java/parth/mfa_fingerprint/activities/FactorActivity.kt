package parth.mfa_fingerprint.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.transition.Slide
import android.view.Gravity
import kotlinx.android.synthetic.main.activity_factors.*
import parth.mfa_fingerprint.R
import parth.mfa_fingerprint.interfaces.MainView
import parth.mfa_fingerprint.types.AuthenticationNode

class FactorActivity : AppCompatActivity(), MainView {

    private val AUTHENTICATION_ONE_COMPLETED = 0
    lateinit var factorOne : AuthenticationNode
    lateinit var factorTwo : AuthenticationNode
    lateinit var factorThree : AuthenticationNode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_factors)
        setSupportActionBar(findViewById(R.id.toolbar))
        setupWindowAnimations()

        val factors: Array<AuthenticationNode> = arrayOf(AuthenticationNode.PASSWORD, AuthenticationNode.QR, AuthenticationNode.FINGERPRINT)
        // Set the factors
        factorOne = factors[0]
        factorTwo = factors[1]
        factorThree = factors[2]
        // Set the factor labels
        factorOneText.text = factorOne.label
        factorTwoText.text = factorTwo.label
        factorThreeText.text = factorThree.label
        //Set on clicks
        factorOneButton.setOnClickListener({
            setFactorOnClick(factorOne.label)
        })
        factorTwoButton.setOnClickListener({
            setFactorOnClick(factorTwo.label)
        })
        factorThreeButton.setOnClickListener({
            setFactorOnClick(factorThree.label)
        })
    }

    private fun setFactorOnClick(s : String) {
        when(s) {
            AuthenticationNode.FINGERPRINT.label -> {
                return fingerprintClick()
            }
            AuthenticationNode.PASSWORD.label -> {
                return passwordClick()
            }
            AuthenticationNode.QR.label -> {
                return qrClick()
            }
            AuthenticationNode.BLANK.label -> {
                return blankClick()
            }
        }
    }

//    override fun auth1Click(view: View) {
//        val a = FingerprintActivity::class.java
//        val intent = Intent(this, a)
//        val options = ActivityOptions.makeSceneTransitionAnimation(this, factorOneText, "factorOneTextTrans")
//        startActivity(intent, options.toBundle())
//    }

//    fun auth3Click(view: View) {
//        val intent = Intent(this, Class.forName(factorThree.className)::class.java)
////        val options = ActivityOptions.makeSceneTransitionAnimation(this,  Pair<View, String>(button3, "factorThreeButtonTrans"), Pair<View, String>(factorThreeText, "factorThreeTitleTrans"))
////        startActivity(intent, options.toBundle())
//        startActivity(intent)
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == AUTHENTICATION_ONE_COMPLETED && resultCode == Activity.RESULT_OK) {
            // Retrieve Authentication result
            val b = data.getBooleanExtra("result", false)
            //  Set the button based on the result
            factorOneButton.isEnabled = !b
        }
    }

    private fun setupWindowAnimations() {
        val slide = Slide()
        slide.duration = 100
        slide.slideEdge = Gravity.LEFT
        window.exitTransition = slide
    }

    fun fingerprintClick() {
        val intent = Intent(this, FingerprintActivity::class.java)
        startActivity(intent)
    }

    fun qrClick() {
        val intent = Intent(this, QrActivity::class.java)
        startActivity(intent)
    }

    fun passwordClick() {
        val intent = Intent(this, PasswordActivity::class.java)
        startActivity(intent)
    }

    fun blankClick() {
        val intent = Intent(this, PasswordActivity::class.java)
        startActivity(intent)
    }
}
