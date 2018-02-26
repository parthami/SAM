package parth.mfa_fingerprint.interfaces

import android.widget.ImageView
import parth.mfa_fingerprint.activities.QrActivity

/**
 * Created by Parth Chandratreya on 24/02/2018.
 */
interface QrPresenterI {

    fun generateMAC(string : String)
    fun generateQRCode(imageView: ImageView)
    fun scanQRCode(activity: QrActivity)
    fun decryptMAC()
}