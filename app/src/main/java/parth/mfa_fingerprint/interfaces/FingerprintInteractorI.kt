package parth.mfa_fingerprint.interfaces

import javax.crypto.Cipher

/**
 * Created by Parth Chandratreya on 31/12/2017.
 */
interface FingerprintInteractorI {

    fun setupKeyStoreAndKeyGenerator()

    fun setupCipher(): Cipher

    fun createKey(keyName: String)
}