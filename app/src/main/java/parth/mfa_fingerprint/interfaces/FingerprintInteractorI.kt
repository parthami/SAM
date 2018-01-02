package parth.mfa_fingerprint.interfaces

import android.content.Context
import javax.crypto.Cipher

/**
 * Created by Parth Chandratreya on 31/12/2017.
 */
interface FingerprintInteractorI {

    fun setupKeyStoreAndKeyGenerator()

    fun setupCipher(): Cipher

    fun createKey(keyName: String)

    fun checkForFingerprints(context : Context)
}