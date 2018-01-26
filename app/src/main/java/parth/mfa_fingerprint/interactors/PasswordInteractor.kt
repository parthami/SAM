package parth.mfa_fingerprint.interactors

import android.app.Activity
import android.content.SharedPreferences
import parth.mfa_fingerprint.helpers.Base64
import parth.mfa_fingerprint.interfaces.PasswordInteractorI

/**
 * Created by Parth Chandratreya on 22/01/2018.
 */
class PasswordInteractor(private val sharedPreferences: SharedPreferences) : PasswordInteractorI, Activity() {

    private val passwordKey: String = "hashedPassword"

    override fun savePassword(encryptedPassword: ByteArray, salt: ByteArray, iv: ByteArray) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("encryptedPassword", Base64.encodeBytes(encryptedPassword))
        editor.putString("salt", Base64.encodeBytes(salt))
        editor.putString("iv", Base64.encodeBytes(iv))
        editor.apply()
    }

    override fun loadPassword(): Array<String> {
        val encryptedPassword = sharedPreferences.getString("encryptedPassword","")
        val salt = sharedPreferences.getString("salt","")
        val iv = sharedPreferences.getString("iv","")
        return arrayOf(encryptedPassword,salt,iv)
    }
}