package parth.mfa_fingerprint.presenters

import android.content.Context
import android.hardware.fingerprint.FingerprintManager
import android.os.CancellationSignal
import android.security.keystore.KeyPermanentlyInvalidatedException
import android.widget.Toast
import parth.mfa_fingerprint.activities.FingerprintActivity
import parth.mfa_fingerprint.interactors.FingerprintInteractor
import parth.mfa_fingerprint.interfaces.FingeprintPresenterI
import java.io.IOException
import java.security.InvalidKeyException
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.UnrecoverableKeyException
import java.security.cert.CertificateException
import javax.crypto.Cipher
import javax.crypto.SecretKey


/**
 * Created by Parth Chandratreya on 31/12/2017.
 */

class FingerprintPresenter constructor(var fingerprintActvity: FingerprintActivity, var fingerprintInteractor: FingerprintInteractor) : FingeprintPresenterI {

    lateinit var fingerprintCipher: Cipher
    val DEFAULT_KEY_NAME = "default_key"

    override fun setupCryto() {
        fingerprintInteractor.setupKeyStoreAndKeyGenerator()
        fingerprintCipher = fingerprintInteractor.setupCipher()
    }

    override fun initCipher(keyName: String): Boolean {
        try {
            fingerprintInteractor.keyStore.load(null)
            fingerprintCipher.init(Cipher.ENCRYPT_MODE, fingerprintInteractor.keyStore.getKey(keyName, null) as SecretKey)
            return true
        } catch (e: Exception) {
            when (e) {
                is KeyPermanentlyInvalidatedException -> return false
                is KeyStoreException,
                is CertificateException,
                is UnrecoverableKeyException,
                is IOException,
                is NoSuchAlgorithmException,
                is InvalidKeyException -> throw RuntimeException("Failed to init Cipher", e)
                else -> throw e
            }
        }
    }

    override fun checkForFingerprints(context: Context) {
        fingerprintInteractor.checkForFingerprints(context)
        fingerprintInteractor.createKey("default_key")
    }


    override fun startListening(context: Context) {
        val cryptoObj: FingerprintManager.CryptoObject = FingerprintManager.CryptoObject(fingerprintCipher)
        val fingerprintHandler: FingerprintHandler = FingerprintHandler(context, cryptoObj)
        fingerprintHandler.startAuthentication()
    }

    class FingerprintHandler(var context: Context, var cryptoObj: FingerprintManager.CryptoObject) : FingerprintManager.AuthenticationCallback() {

        private var cancellationSignal: CancellationSignal? = null

        fun startAuthentication() {
            var fingerprintMgr: FingerprintManager =  context.getSystemService(FingerprintManager::class.java)
            fingerprintMgr.authenticate(cryptoObj, CancellationSignal(), 0, this, null)
        }

        override fun onAuthenticationError(errMsgId: Int, errString: CharSequence) {
            Toast.makeText(context, "Authentication error\n" + errString, Toast.LENGTH_LONG).show()
        }

        override fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence) {
            Toast.makeText(context, "Authentication help\n" + helpString, Toast.LENGTH_LONG).show()
        }

        override fun onAuthenticationFailed() {
            Toast.makeText(context, "Authentication failed.", Toast.LENGTH_LONG).show()
        }

        override fun onAuthenticationSucceeded(result: FingerprintManager.AuthenticationResult) {
            Toast.makeText(context, "Authentication succeeded.", Toast.LENGTH_LONG).show()
        }
    }

}