package parth.mfa_fingerprint.interfaces

import android.content.Intent
import android.view.View

/**
 * Created by Parth Chandratreya on 02/01/2018.
 */
interface MainView {
    fun setFactorOnClick(s: String, resultCode: Int)
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    fun setupWindowAnimations()
    fun onClick(v: View)
    fun fingerprintClick(resultCode: Int)
    fun qrClick(resultCode: Int)
    fun passwordClick(resultCode: Int)
    fun blankClick(resultCode: Int)
    fun locationClick(resultCode: Int)
    fun voiceClick(resultCode: Int)
    fun oneTimeClick(resultCode: Int)
    fun finishedClick()
}