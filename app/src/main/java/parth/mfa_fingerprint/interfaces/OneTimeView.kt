package parth.mfa_fingerprint.interfaces;

import android.view.View

/**
 * Created by Parth Chandratreya on 21/03/2018.
 */

interface OneTimeView {
    fun check(v: View)
    fun displaySnackbar(display: String)
    fun onResult(boolean: Boolean)
}
