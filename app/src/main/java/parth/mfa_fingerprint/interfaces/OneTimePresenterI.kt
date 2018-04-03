package parth.mfa_fingerprint.interfaces;

import android.content.Context

/**
 * Created by Parth Chandratreya on 21/03/2018.
 */

interface OneTimePresenterI {
    fun emailPassword(context: Context)
    fun checkPassword(stringToCheck: String): Boolean
    fun generatePassword(b: Boolean)
}
