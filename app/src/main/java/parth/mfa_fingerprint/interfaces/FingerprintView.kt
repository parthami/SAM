package parth.mfa_fingerprint.interfaces

import javax.crypto.Cipher

/**
 * Created by Parth Chandratreya on 28/12/2017.
 */
interface FingerprintView {

    fun setupKeyStoreAndKeyGenerator()

    fun setupCiphers(): Cipher

    fun fingerprintExists()

    fun createKey(keyName: String)
}