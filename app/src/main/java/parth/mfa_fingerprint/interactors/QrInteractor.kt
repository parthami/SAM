package parth.mfa_fingerprint.interactors

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import parth.mfa_fingerprint.interfaces.QrInteractorI
import javax.crypto.KeyGenerator
import javax.crypto.Mac


/**
 * Created by Parth Chandratreya on 24/02/2018.
 */
class QrInteractor : QrInteractorI {

    override fun encryptMAC(string: String ) : ByteArray {
        // Generate HMAC
        val keyGenerator = KeyGenerator.getInstance( KeyProperties.KEY_ALGORITHM_HMAC_SHA256, "AndroidKeyStore")
        keyGenerator.init(KeyGenParameterSpec.Builder("key1", KeyProperties.PURPOSE_SIGN).build())
        val key = keyGenerator.generateKey()
        // Generate MAC
        val mac = Mac.getInstance("HmacSHA256")
        mac.init(key)
        return mac.doFinal(string.toByteArray())
    }

    override fun decryptMAC() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun compareMACs() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun generateMac(message : String) {


    }

}