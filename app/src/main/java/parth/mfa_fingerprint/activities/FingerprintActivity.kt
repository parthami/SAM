package parth.mfa_fingerprint.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_fingerprint.*
import parth.mfa_fingerprint.R
import parth.mfa_fingerprint.interactors.FingerprintInteractor
import parth.mfa_fingerprint.interfaces.FingerprintView
import parth.mfa_fingerprint.presenters.FingerprintPresenter

class FingerprintActivity : AppCompatActivity(), FingerprintView {

    lateinit var presenter: FingerprintPresenter
    lateinit var interactor: FingerprintInteractor
    var active : Boolean = true
    var authenticationCompleted = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fingerprint)
        /* Create the Presenter and Interactor */
        interactor = FingerprintInteractor()
        presenter = FingerprintPresenter(this, interactor)
        /* Setup the listeners */
        toggleButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                intent.extras?.putBoolean("active", false)
                toggleButton.isEnabled = false
            }
        }

        presenter.setupCryto()
        presenter.checkForFingerprints(applicationContext)
        presenter.initCipher("default_key")
        presenter.startListening(this)
    }

    override fun onCompletion() {
        intent.extras?.putBoolean("active", false)
    }
}
