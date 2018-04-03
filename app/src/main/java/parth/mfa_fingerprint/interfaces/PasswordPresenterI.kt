package parth.mfa_fingerprint.interfaces

import android.text.Editable
import javax.crypto.spec.IvParameterSpec

/**
 * Created by Parth Chandratreya on 22/01/2018.
 */
interface  PasswordPresenterI {
    fun hashPassword(passwordToEncrypt: String)
    fun savePassword(encryptedPassword: ByteArray, salt: ByteArray, iv: ByteArray)
    fun comparePassword(editable: Editable): Boolean
    fun hashGeneration(passwordToEncrypt: String, salt: ByteArray, ivParams: IvParameterSpec): ByteArray
}