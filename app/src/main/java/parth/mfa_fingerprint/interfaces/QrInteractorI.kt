package parth.mfa_fingerprint.interfaces

/**
 * Created by Parth Chandratreya on 24/02/2018.
 */
interface QrInteractorI {
    fun encryptMAC(string : String) : ByteArray
    fun compareMACs(s1: String, s2: String): Boolean
}