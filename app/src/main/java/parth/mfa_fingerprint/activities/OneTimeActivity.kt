package parth.mfa_fingerprint.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.PreferenceManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import parth.mfa_fingerprint.R
import parth.mfa_fingerprint.interactors.OneTimeInteractor
import parth.mfa_fingerprint.interfaces.OneTimeView
import parth.mfa_fingerprint.presenters.OneTimePresenter


class OneTimeActivity : AppCompatActivity(), OneTimeView {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var inputManager: InputMethodManager
    private lateinit var interactor: OneTimeInteractor
    private lateinit var presenter: OneTimePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_time)
        setSupportActionBar(findViewById(R.id.toolbar))
        inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        sharedPreferences = getPreferences(android.content.Context.MODE_PRIVATE)
        val passwordPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val demoMode = passwordPreferences.getBoolean("demo_switch", false)

        interactor = OneTimeInteractor()
        presenter = OneTimePresenter(this, interactor)

        // Generate OTP
        presenter.generatePassword(demoMode)
        // Email the OTP
        presenter.emailPassword(this)
    }

    override fun check(v: View) {
        inputManager.hideSoftInputFromWindow(if (null == currentFocus) null else currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        val bool = presenter.checkPassword(oneTimeCheckEditText.text.toString())
        presenter.displayMessage(bool)
        onResult(bool)
    }

    override fun displaySnackbar(display: String) {
        Snackbar.make(constraintLayout2, display, Snackbar.LENGTH_SHORT).show()
    }

    override fun onResult(boolean: Boolean) {
        val intent = Intent()
        intent.putExtra("result", boolean)
        setResult(Activity.RESULT_OK, intent)
        finishAfterTransition()
    }

}
