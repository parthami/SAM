package parth.mfa_fingerprint.interfaces

import android.text.Editable

/**
 * Created by Parth Chandratreya on 22/01/2018.
 */
interface  PasswordPresenterI {
    fun hashPassword(editable: Editable){}
    fun savePassword(editable: Editable){}
    fun comparePassword(){}
}