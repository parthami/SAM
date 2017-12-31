package parth.mfa_fingerprint.interfaces

/**
 * Created by Parth Chandratreya on 31/12/2017.
 */
interface FingeprintPresenterI {

    fun setupCryto ()
    fun initCipher(keyName: String): Boolean
    fun startListening()

}