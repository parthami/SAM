package parth.mfa_fingerprint.interactors

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Log
import parth.mfa_fingerprint.helpers.Base64
import parth.mfa_fingerprint.interfaces.QrInteractorI
import java.security.KeyStore
import javax.crypto.KeyGenerator
import javax.crypto.Mac


/**
 * Created by Parth Chandratreya on 24/02/2018.
 */
class QrInteractor : QrInteractorI {

    val hmacKeyAlias : String = "key1"
//    private lateinit var  key: SecretKey

    fun generateKey() {
        // Generate HMAC
        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_HMAC_SHA256, "AndroidKeyStore")
        keyGenerator.init(KeyGenParameterSpec.Builder(hmacKeyAlias, KeyProperties.PURPOSE_SIGN).build())
        val key = keyGenerator.generateKey()
    }

    override fun encryptMAC(string: String) : ByteArray {
        val keyStore = KeyStore.getInstance("AndroidKeyStore")
        keyStore.load(null,null)
        val key = keyStore.getKey(hmacKeyAlias, null)
        val mac = Mac.getInstance(key.algorithm)
        mac.init(key)
        // Return the HMAC
        return mac.doFinal(string.toByteArray())
    }

    override fun compareMACs(originalIdentifier : String, qrIdentifer: String): Boolean {
        Log.i("PTAG", "originalIdentifier:$originalIdentifier")
        val decodedIdentifier = Base64.decode(qrIdentifer)
        Log.i("PTAG", "qrIdentifer : $decodedIdentifier")
        // Retrieve Key
        val keyStore = KeyStore.getInstance("AndroidKeyStore")
        keyStore.load(null,null)
        val key = keyStore.getKey(hmacKeyAlias, null)
        // Generate mac
        val mac = Mac.getInstance(key.algorithm)
        mac.init(key)
        // Compare MAC
        val reencryptedIdentifier = mac.doFinal(originalIdentifier.toByteArray())
        val newMAC = Base64.encodeBytes(reencryptedIdentifier)
        Log.i("PTAG", "newMac: $newMAC")
        return newMAC.contentEquals(qrIdentifer)
    }

}