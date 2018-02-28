package parth.mfa_fingerprint.interfaces

import android.view.View

/**
 * Created by Parth Chandratreya on 24/02/2018.
 */
interface QrView {
    fun createMAC()
    fun createQR(view : View)
    fun launchCamera(view: View)
    fun authenticate(v: View)
}