package parth.mfa_fingerprint.interactors

import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import parth.mfa_fingerprint.interfaces.FingerprintInteractorI
import java.io.IOException
import java.security.*
import java.security.cert.CertificateException
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.NoSuchPaddingException

/**
 * Created by Parth Chandratreya on 31/12/2017.
 */
class FingerprintInteractor : FingerprintInteractorI {

    lateinit var keyStore: KeyStore
    private lateinit var keyGenerator: KeyGenerator
    private lateinit var sharedPreferences: SharedPreferences
    private val ANDROID_KEY_STORE = "AndroidKeyStore"

    /**
     * Sets up KeyStore and KeyGenerator
     */
    override fun setupKeyStoreAndKeyGenerator() {
        try {
            keyStore = KeyStore.getInstance(ANDROID_KEY_STORE)
        } catch (e: KeyStoreException) {
            throw RuntimeException("Failed to get an instance of KeyStore", e)
        }

        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEY_STORE)
        } catch (e: Exception) {
            when (e) {
                is NoSuchAlgorithmException,
                is NoSuchProviderException ->
                    throw RuntimeException("Failed to get an instance of KeyGenerator", e)
                else -> throw e
            }
        }
    }

    /**
     * Sets up default cipher
     */
    override fun setupCipher(): Cipher {
        val defaultCipher: Cipher
        try {
            val cipherString = "${KeyProperties.KEY_ALGORITHM_AES}/${KeyProperties.BLOCK_MODE_CBC}/${KeyProperties.ENCRYPTION_PADDING_PKCS7}"
            defaultCipher = Cipher.getInstance(cipherString)
        } catch (e: Exception) {
            when (e) {
                is NoSuchAlgorithmException,
                is NoSuchPaddingException ->
                    throw RuntimeException("Failed to get an instance of Cipher", e)
                else -> throw e
            }
        }
        return defaultCipher
    }

    /**
     * Creates a symmetric key in the Android Key Store which can only be used after the user has
     * authenticated with a fingerprint.
     *
     * @param keyName the name of the key to be created
     */
    override fun createKey(keyName: String) {
        // The enrolling flow for fingerprint. This is where you ask the user to set up fingerprint
        // for your flow. Use of keys is necessary if you need to know if the set of enrolled
        // fingerprints has changed.
        try {
            keyStore.load(null)

            val keyProperties = KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            val builder = KeyGenParameterSpec.Builder(keyName, keyProperties)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)

            keyGenerator.run {
                init(builder.build())
                generateKey()
            }
        } catch (e: Exception) {
            when (e) {
                is NoSuchAlgorithmException,
                is InvalidAlgorithmParameterException,
                is CertificateException,
                is IOException -> throw RuntimeException(e)
                else -> throw e
            }
        }
    }
}