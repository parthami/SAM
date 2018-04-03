package parth.mfa_fingerprint.presenters;

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import parth.mfa_fingerprint.activities.OneTimeActivity
import parth.mfa_fingerprint.interactors.OneTimeInteractor
import parth.mfa_fingerprint.interfaces.OneTimePresenterI
import java.util.concurrent.ThreadLocalRandom

/**
 * Created by Parth Chandratreya on 21/03/2018.
 */

class OneTimePresenter(private val activity: OneTimeActivity, private val interactor: OneTimeInteractor) : OneTimePresenterI {

    private var otp: Int = 0

    override fun emailPassword(context: Context) {
        val i = Intent(Intent.ACTION_SEND)
        i.type = "message/rfc822"
        i.putExtra(Intent.EXTRA_EMAIL, arrayOf("chandratreya.parth@gmail.com"))
        i.putExtra(Intent.EXTRA_SUBJECT, "SAM OTP")
        i.putExtra(Intent.EXTRA_TEXT, "The generated OTP is $otp")
        try {
            startActivity(context,Intent.createChooser(i, "Send mail..."),null)
        } catch (ex: android.content.ActivityNotFoundException) {
            activity.displaySnackbar("There are no email clients installed.")
        }
    }

    override fun checkPassword(stringToCheck: String): Boolean {
        return stringToCheck == otp.toString()
    }

    fun displayMessage(b: Boolean){
        val snackbarString = if (b) {
            "OTP verified!"
        } else {
            "WRONG OTP!"
        }
        activity.displaySnackbar(snackbarString)
    }

    override fun generatePassword(b: Boolean) {
        otp = if (b) {
            ThreadLocalRandom.current().nextInt(111111, 999999)
        } else {
            210796
        }
    }
}
