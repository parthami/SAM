package parth.mfa_fingerprint.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_fingerprint.*
import parth.mfa_fingerprint.R
import parth.mfa_fingerprint.interactors.FingerprintInteractor
import parth.mfa_fingerprint.interfaces.FingerprintView
import parth.mfa_fingerprint.presenters.FingerprintPresenter

class FingerprintActivity : AppCompatActivity(), FingerprintView {

    private lateinit var presenter: FingerprintPresenter
    private lateinit var interactor: FingerprintInteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fingerprint)
        /* Create the Presenter and Interactor */
        interactor = FingerprintInteractor()
        presenter = FingerprintPresenter(interactor)
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
