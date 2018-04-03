package parth.mfa_fingerprint.interfaces

import android.view.View

/**
 * Created by Parth Chandratreya on 22/01/2018.
 */
interface PasswordView {
    fun saveClick(view: View)
    fun checkClick(view: View)
    fun setupWindowAnimations()
    fun onResult(boolean: Boolean)
}