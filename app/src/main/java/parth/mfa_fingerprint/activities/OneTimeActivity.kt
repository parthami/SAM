package parth.mfa_fingerprint.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_one_time.*
import parth.mfa_fingerprint.R







class OneTimeActivity : AppCompatActivity() {

    var otp : Int = 12345
    lateinit var inputManager : InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_time)
        setSupportActionBar(findViewById(R.id.toolbar))
        inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        // Generate OTP
        // Email the OTP
        val i = Intent(Intent.ACTION_SEND)
        i.type = "message/rfc822"
        i.putExtra(Intent.EXTRA_EMAIL, arrayOf("chandratreya.parth@gmail.com"))
        i.putExtra(Intent.EXTRA_SUBJECT, "SAM OTP")
        i.putExtra(Intent.EXTRA_TEXT, "$otp")
        try {
            startActivity(Intent.createChooser(i, "Send mail..."))
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show()
        }
    }

    fun check(v : View) {
        inputManager.hideSoftInputFromWindow(if (null == currentFocus) null else currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        if(editText.text.toString() == otp.toString()) {
            Snackbar.make(constraintLayout2, "OTP verified!", Snackbar.LENGTH_SHORT).show()
        } else {
            Snackbar.make(constraintLayout2, "WRONG OTP!", Snackbar.LENGTH_SHORT).show()
        }
    }

}
