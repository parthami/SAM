package parth.mfa_fingerprint.interfaces

/**
 * Created by Parth Chandratreya on 22/01/2018.
 */
interface PasswordInteractorI {
    fun loadPassword(): Array<String>
    fun savePassword(encryptedPassword: ByteArray, salt: ByteArray, iv: ByteArray)
}