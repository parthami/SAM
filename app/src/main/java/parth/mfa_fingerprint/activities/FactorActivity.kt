package parth.mfa_fingerprint.activities

import android.app.Activity
import android.arch.persistence.room.Room
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.transition.Slide
import android.util.Log
import android.view.Gravity
import android.view.View
import org.jetbrains.anko.doAsync
import parth.mfa_fingerprint.R
import parth.mfa_fingerprint.interfaces.MainView
import parth.mfa_fingerprint.room.AppDatabase
import parth.mfa_fingerprint.room.AuthenticationNodeLog
import parth.mfa_fingerprint.types.AuthenticationNode
import java.util.*

class FactorActivity : AppCompatActivity(), MainView {

    private val authentication_one_result = 0
    private val authentication_two_result = 1
    private val authentication_three_result = 2
    lateinit var factorOne: AuthenticationNode
    lateinit var factorTwo: AuthenticationNode
    lateinit var factorThree: AuthenticationNode
    private var factorCounter: Int = 0
    private var db = Room.databaseBuilder(this, AppDatabase::class.java, "authenticationLogs").build()
    private lateinit var authenticationLog: AuthenticationNodeLog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_factors)
        setSupportActionBar(findViewById(R.id.toolbar))
        setupWindowAnimations()

        val factors = intent.getStringArrayListExtra("factors")
//        val factors: Array<AuthenticationNode> = arrayOf(AuthenticationNode.PASSWORD, AuthenticationNode.QR, AuthenticationNode.FINGERPRINT, AuthenticationNode.BLANK)
        // Set the factors
        val factorOneString = factors[0]
        val factorTwoString = factors[1]
        val factorThreeString = factors[2]

        factorOne = AuthenticationNode.valueOf(factorOneString)
        factorTwo = AuthenticationNode.valueOf(factorTwoString)
        factorThree = AuthenticationNode.valueOf(factorThreeString)
        // Set the factor labels
        factorOneText.text = factorOne.label
        factorTwoText.text = factorTwo.label
        factorThreeText.text = factorThree.label
        //Set on clicks
        factorOneButton.setOnClickListener({
            setFactorOnClick(factorOne.label, authentication_one_result)
        })
        factorTwoButton.setOnClickListener({
            setFactorOnClick(factorTwo.label, authentication_two_result)
        })
        factorThreeButton.setOnClickListener({
            setFactorOnClick(factorThree.label, authentication_three_result)
        })

        authenticationLog = AuthenticationNodeLog(factorOne.label, factorTwo.label, factorThree.label, false, false, false, Date().time)
    }

    override fun setFactorOnClick(s: String, resultCode: Int) {
        when (s) {
            AuthenticationNode.FINGERPRINT.label -> {
                return fingerprintClick(resultCode)
            }
            AuthenticationNode.PASSWORD.label -> {
                return passwordClick(resultCode)
            }
            AuthenticationNode.QR.label -> {
                return qrClick(resultCode)
            }
            AuthenticationNode.BLANK.label -> {
                return blankClick(resultCode)
            }
            AuthenticationNode.LOCATION.label -> {
                return locationClick(resultCode)
            }
            AuthenticationNode.ONETIME.label -> {
                return oneTimeClick(resultCode)
            }
            AuthenticationNode.VOICE.label -> {
                return voiceClick(resultCode)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_CANCELED || data != null) {
            if (requestCode == authentication_one_result && resultCode == Activity.RESULT_OK) {
                val b = data!!.getBooleanExtra("result", false)
                //  Set the button based on the result
                factorOneButton.isEnabled = false
                factorOneButton.setTextColor(ContextCompat.getColor(this, R.color.secondaryColor))
                factorOneText.typeface = Typeface.DEFAULT_BOLD
                factorOneButton.setText(if (b) R.string.authenticationSuccess else R.string.authenticationFailure)
                authenticationLog.result = b
                AuthenticationNode.changeProbability(factorOne, b)
            }
            if (requestCode == authentication_two_result && resultCode == Activity.RESULT_OK) {
                val b = data!!.getBooleanExtra("result", false)
                //  Set the button based on the result
                factorTwoButton.isEnabled = false
                factorTwoButton.setTextColor(ContextCompat.getColor(this, R.color.secondaryColor))
                factorTwoText.typeface = Typeface.DEFAULT_BOLD
                factorTwoButton.setText(if (b) R.string.authenticationSuccess else R.string.authenticationFailure)
                authenticationLog.result2 = b
                AuthenticationNode.changeProbability(factorTwo, b)
            }
            if (requestCode == authentication_three_result && resultCode == Activity.RESULT_OK) {
                val b = data!!.getBooleanExtra("result", false)
                //  Set the button based on the result
                factorThreeButton.isEnabled = false
                factorThreeButton.setTextColor(ContextCompat.getColor(this, R.color.secondaryColor))
                factorThreeText.typeface = Typeface.DEFAULT_BOLD
                factorThreeButton.setText(if (b) R.string.authenticationSuccess else R.string.authenticationFailure)
                authenticationLog.result3 = b
                AuthenticationNode.changeProbability(factorThree, b)
            }
            // COUNTER
            factorCounter++
            if (factorCounter < 3) {
                continueButton.text = "$factorCounter/3 Factors completed"
            } else if (factorCounter == 3) {
                continueButton.text = "Authentication complete!"
                continueButton.isEnabled = true
                continueButton.setOnClickListener({
                    Log.i("PTAG", "Finished!")
                    finishedClick()
                })
            }
        }
    }

    override fun setupWindowAnimations() {
        val slide = Slide()
        slide.duration = 100
        slide.slideEdge = Gravity.LEFT
        window.exitTransition = slide
    }

    override fun onClick(v: View) {
        val intent = Intent(this, AuthenticationLogActivity::class.java)
        startActivity(intent)
    }

    override fun fingerprintClick(resultCode: Int) {
        val intent = Intent(this, FingerprintActivity::class.java)
        startActivityForResult(intent, resultCode)
    }

    override fun qrClick(resultCode: Int) {
        val intent = Intent(this, QrActivity::class.java)
        startActivityForResult(intent, resultCode)
    }

    override fun passwordClick(resultCode: Int) {
        val intent = Intent(this, PasswordActivity::class.java)
        startActivityForResult(intent, resultCode)
    }

    override fun blankClick(resultCode: Int) {
        val intent = Intent(this, BlankActivity::class.java)
        startActivityForResult(intent, resultCode)
    }

    override fun locationClick(resultCode: Int) {
        val intent = Intent(this, LocationActivity::class.java)
        startActivityForResult(intent, resultCode)
    }

    override fun voiceClick(resultCode: Int) {
        val intent = Intent(this, VoiceActivity::class.java)
        startActivityForResult(intent, resultCode)
    }

    override fun oneTimeClick(resultCode: Int) {
        val intent = Intent(this, OneTimeActivity::class.java)
        startActivityForResult(intent, resultCode)
    }

    override fun finishedClick() {
        val intent = Intent(this, AuthenticationLogActivity::class.java)
        doAsync {
            db.authenticationNodeLogDAO().insertLog(authenticationLog)
        }
        startActivity(intent)

    }
}
