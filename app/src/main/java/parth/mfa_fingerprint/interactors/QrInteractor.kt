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

    override fun encryptMAC(string: String ) : ByteArray {
        // Generate HMAC
        val keyGenerator = KeyGenerator.getInstance( KeyProperties.KEY_ALGORITHM_HMAC_SHA256, "AndroidKeyStore")
        keyGenerator.init(KeyGenParameterSpec.Builder(hmacKeyAlias, KeyProperties.PURPOSE_SIGN).build())
        val key = keyGenerator.generateKey()
        // Generate MAC
        val mac = Mac.getInstance("HmacSHA256")
        mac.init(key)
        return mac.doFinal(string.toByteArray())
    }

    override fun compareMACs(originalIdentifier : String, encryptedIdentifier: ByteArray): Boolean {
        Log.i("PTAG", "originalIdentifier:${Base64.decode(originalIdentifier)}")
        Log.i("PTAG", "encryptedIdentifier : ${Base64.encodeBytes(encryptedIdentifier)}")
        val keyStore = KeyStore.getInstance("AndroidKeyStore")
        keyStore.load(null,null)
        val key = keyStore.getKey(hmacKeyAlias, null)
        val mac = Mac.getInstance("HmacSHA256")
        mac.init(key)
        val reencryptedIdentifier = mac.doFinal(originalIdentifier.toByteArray())
        Log.i("PTAG", "reencryptedIdentifier: ${Base64.decode(reencryptedIdentifier)}")
//        return compareValues(reencryptedIdentifier, encryptedIdentifier)
        return reencryptedIdentifier.contentEquals(encryptedIdentifier)
    }

}