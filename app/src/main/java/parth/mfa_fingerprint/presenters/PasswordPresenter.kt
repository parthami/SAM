package parth.mfa_fingerprint.presenters

import android.text.Editable
import com.amdelamar.jhash.Hash
import parth.mfa_fingerprint.interactors.PasswordInteractor
import parth.mfa_fingerprint.interfaces.PasswordPresenterI
import parth.mfa_fingerprint.interfaces.PasswordView

/**
 * Created by Parth Chandratreya on 22/01/2018.
 */
class PasswordPresenter(val view: PasswordView, private val interactor : PasswordInteractor): PasswordPresenterI {

    lateinit var password : CharArray

    override fun hashPassword(editable: Editable){
        editable.getChars(0,editable.length,password,0)
    }
    override fun savePassword(){
        val hash : String = Hash.password(password).create()
        interactor.savePassword(hash)
    }
    override fun comparePassword() : Boolean{
        val hashedPassword : String = interactor.loadPassword()
        return Hash.password(password).verify(hashedPassword)
    }
}