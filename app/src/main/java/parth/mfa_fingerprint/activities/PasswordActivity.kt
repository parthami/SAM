package parth.mfa_fingerprint.activities

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_password.*
import parth.mfa_fingerprint.R
import parth.mfa_fingerprint.interactors.PasswordInteractor
import parth.mfa_fingerprint.interfaces.PasswordView
import parth.mfa_fingerprint.presenters.PasswordPresenter

class PasswordActivity : Activity(), PasswordView {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var interactor: PasswordInteractor
    private lateinit var presenter: PasswordPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)

        sharedPreferences = getPreferences(android.content.Context.MODE_PRIVATE)
        interactor = PasswordInteractor(sharedPreferences)
        presenter = PasswordPresenter(this, interactor)
    }

    override fun checkClick(view: View) {
        // Compare the password
        if (presenter.comparePassword(passwordField.text)) {
            Toast.makeText(this, "Pass word verified!", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Password incorrect", Toast.LENGTH_LONG).show()
        }
    }

    override fun saveClick(view: View) {
        // Hash the password
        presenter.hashPassword(passwordField.text)
        Toast.makeText(this, "Password saved!", Toast.LENGTH_LONG).show()

    }
}
