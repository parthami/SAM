package parth.mfa_fingerprint.presenters

import android.content.Context
import android.hardware.fingerprint.FingerprintManager
import android.os.CancellationSignal
import android.security.keystore.KeyPermanentlyInvalidatedException
import android.widget.Toast
import parth.mfa_fingerprint.interactors.FingerprintInteractor
import parth.mfa_fingerprint.interfaces.FingeprintPresenterI
import parth.mfa_fingerprint.interfaces.FingerprintView
import parth.mfa_fingerprint.types.AuthenticationNode
import java.io.IOException
import java.security.InvalidKeyException
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.UnrecoverableKeyException
import java.security.cert.CertificateException
import java.time.LocalDateTime
import javax.crypto.Cipher
import javax.crypto.SecretKey


/**
 * Created by Parth Chandratreya on 31/12/2017.
 */

class FingerprintPresenter (private var fingerprintView: FingerprintView, private var fingerprintInteractor: FingerprintInteractor) : FingeprintPresenterI {

    private lateinit var fingerprintCipher: Cipher

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
            return when (e) {
                is KeyPermanentlyInvalidatedException -> false
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
        val fingerprintHandler = FingerprintHandler(context, cryptoObj, fingerprintView)
        fingerprintHandler.startAuthentication()
    }

    class FingerprintHandler(private var context: Context, private var cryptoObj: FingerprintManager.CryptoObject, private var fingerprintView: FingerprintView) : FingerprintManager.AuthenticationCallback() {

        private var node = AuthenticationNode.FINGERPRINT
        private var datetime = LocalDateTime.now()

        fun startAuthentication() {
            val fingerprintMgr: FingerprintManager =  context.getSystemService(FingerprintManager::class.java)
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
//            var log = AuthenticationNodeLog(0, node.label, true)
            fingerprintView.onSuccess()
        }
    }

}