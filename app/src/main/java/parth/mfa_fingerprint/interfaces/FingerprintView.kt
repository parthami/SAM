package parth.mfa_fingerprint.interfaces

/**
 * Created by Parth Chandratreya on 28/12/2017.
 */
interface FingerprintView {

    fun onResult(boolean: Boolean)
    fun success()
    fun setupWindowAnimations()
}