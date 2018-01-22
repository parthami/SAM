package parth.mfa_fingerprint.presenters

import android.text.Editable
import parth.mfa_fingerprint.interactors.PasswordInteractor
import parth.mfa_fingerprint.interfaces.PasswordPresenterI
import parth.mfa_fingerprint.interfaces.PasswordView

/**
 * Created by Parth Chandratreya on 22/01/2018.
 */
class PasswordPresenter(view: PasswordView, interactor : PasswordInteractor): PasswordPresenterI {
    override fun hashPassword(editable: Editable){}
    override fun savePassword(editable: Editable){}
    override fun comparePassword(){}
}