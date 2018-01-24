package parth.mfa_fingerprint.interactors

import android.app.Activity
import android.content.SharedPreferences
import parth.mfa_fingerprint.interfaces.PasswordInteractorI

/**
 * Created by Parth Chandratreya on 22/01/2018.
 */
class PasswordInteractor : PasswordInteractorI, Activity() {
    private lateinit var settings: SharedPreferences
    private val passwordKey: String = "hashedPassword"

    override fun savePassword(string: String) {
        settings = getPreferences(android.content.Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = settings.edit()
        editor.putString(passwordKey, string)
        editor.apply()
    }

    override fun loadPassword(): String {
        settings = getPreferences(android.content.Context.MODE_PRIVATE)
        return settings.getString(passwordKey,"")
    }
}