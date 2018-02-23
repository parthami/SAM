package parth.mfa_fingerprint.activities

import android.app.Activity
import android.arch.persistence.room.Room
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_password.*
import org.jetbrains.anko.doAsync
import parth.mfa_fingerprint.R
import parth.mfa_fingerprint.interactors.PasswordInteractor
import parth.mfa_fingerprint.interfaces.PasswordView
import parth.mfa_fingerprint.presenters.PasswordPresenter
import parth.mfa_fingerprint.room.AppDatabase
import parth.mfa_fingerprint.room.AuthenticationNodeLog
import parth.mfa_fingerprint.types.AuthenticationNode
import java.util.*

class PasswordActivity : Activity(), PasswordView {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var interactor: PasswordInteractor
    private lateinit var presenter: PasswordPresenter
    private var node = AuthenticationNode.PASSWORD
    private var db = Room.databaseBuilder(this, AppDatabase::class.java, "authenticationLogs").build()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)

        sharedPreferences = getPreferences(android.content.Context.MODE_PRIVATE)
        interactor = PasswordInteractor(sharedPreferences)
        presenter = PasswordPresenter(this, interactor)
    }

    override fun checkClick(view: View) {
        // Compare the password
        val log = if (presenter.comparePassword(passwordField.text)) {
            Toast.makeText(this, "Password verified!", Toast.LENGTH_LONG).show()
            AuthenticationNodeLog(node.label, true, Date().time)
        } else {
            Toast.makeText(this, "Password incorrect", Toast.LENGTH_LONG).show()
            AuthenticationNodeLog(node.label, false,  Date().time)
        }
        doAsync {
            db.authenticationNodeLogDAO().insertLog(log)
        }
    }

    override fun saveClick(view: View) {
        // Hash the password
        presenter.hashPassword(passwordField.text)
        Toast.makeText(this, "Password saved!", Toast.LENGTH_LONG).show()
        passwordField.text.clear()

    }
}
