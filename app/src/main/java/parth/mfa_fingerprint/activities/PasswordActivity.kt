package parth.mfa_fingerprint.activities

import android.arch.persistence.room.Room
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.PreferenceManager
import android.transition.Slide
import android.view.Gravity
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

class PasswordActivity : AppCompatActivity(), PasswordView {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var interactor: PasswordInteractor
    private lateinit var presenter: PasswordPresenter
    private var node = AuthenticationNode.PASSWORD
    private var db = Room.databaseBuilder(this, AppDatabase::class.java, "authenticationLogs").build()


    override fun onCreate(savedInstanceState: Bundle?) {
        // setup
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)
        setSupportActionBar(findViewById(R.id.toolbar))
        setupWindowAnimations()
        // Shared Pref
        sharedPreferences = getPreferences(android.content.Context.MODE_PRIVATE)
        val passwordPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val savedPassword = passwordPreferences.getString("pref_key_password", "password")
        savedPasswordExample.text = "Current saved password : $savedPassword"
        // Load presenter and interactor
        interactor = PasswordInteractor(sharedPreferences)
        presenter = PasswordPresenter(this, interactor)

        presenter.hashPassword(savedPassword.toCharArray())
    }

    override fun checkClick(view: View) {
        // Compare the password
        // TODO fix empty password check
        val log = if (presenter.comparePassword(passwordField.text)) {
            Snackbar.make(constraintLayout, "Password verified!", Snackbar.LENGTH_SHORT).show()
            AuthenticationNodeLog(node.label, true, Date().time)
        } else {
            Snackbar.make(constraintLayout, "Password incorrect", Snackbar.LENGTH_SHORT).show()
            AuthenticationNodeLog(node.label, false,  Date().time)
        }
        doAsync {
            db.authenticationNodeLogDAO().insertLog(log)
        }
    }

    override fun saveClick(view: View) {
        // Hash the password
//        presenter.hashPassword(passwordField.text)
        Toast.makeText(this, "Password saved!", Toast.LENGTH_LONG).show()
        passwordField.text.clear()

    }

    private fun setupWindowAnimations() {
        val slide = Slide()
        slide.duration = 100
        slide.slideEdge = Gravity.LEFT
        window.enterTransition = slide
    }
}
