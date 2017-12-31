package parth.mfa_fingerprint.presenters

import android.hardware.fingerprint.FingerprintManager
import android.os.CancellationSignal
import android.security.keystore.KeyPermanentlyInvalidatedException
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

class FingerprintPresenter constructor(fingerprintActvity: FingerprintActivity, fingerprintInteractor: FingerprintInteractor) : FingeprintPresenterI {

    var fingerprintActvity = fingerprintActvity
    var fingerprintInteractor = fingerprintInteractor
    lateinit var fingerprintCipher: Cipher
    private var cancellationSignal: CancellationSignal? = null
    private lateinit var  fingerprintMgr: FingerprintManager

    val DEFAULT_KEY_NAME = "default_key"

    override fun setupCryto() {
        fingerprintInteractor.setupKeyStoreAndKeyGenerator()
        fingerprintCipher = fingerprintInteractor.setupCipher()
    }

    override fun initCipher(keyName: String) : Boolean {
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

    override fun startListening() {
        var cryptoObj: FingerprintManager.CryptoObject = FingerprintManager.CryptoObject(fingerprintCipher)
        fingerprintMgr.authenticate(cryptoObj, CancellationSignal(), 0, null, null)
    }

}