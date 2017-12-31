package parth.mfa_fingerprint.activities

import android.app.KeyguardManager
import android.hardware.fingerprint.FingerprintManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import parth.mfa_fingerprint.R
import parth.mfa_fingerprint.interactors.FingerprintInteractor
import parth.mfa_fingerprint.interfaces.FingerprintView
import parth.mfa_fingerprint.presenters.FingerprintPresenter

class FingerprintActivity : AppCompatActivity(), FingerprintView {

    private lateinit var interactor: FingerprintInteractor
    private lateinit var presenter: FingerprintPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fingerprint)

//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        presenter = FingerprintPresenter(this, interactor)
        presenter.setupCryto()
        checkForFingerprints()
        presenter.initCipher("default_key")
        presenter.startListening()
    }

    override fun checkForFingerprints() {
        val keyguardManager = getSystemService(KeyguardManager::class.java)
        if (!keyguardManager.isKeyguardSecure) {
            // Show a message that the user hasn't set up a fingerprint or lock screen.
            Toast.makeText(this, getString(R.string.setup_lock_screen), Toast.LENGTH_LONG).show()
            return
        }

        val fingerprintManager = getSystemService(FingerprintManager::class.java)
        if (!fingerprintManager.hasEnrolledFingerprints()) {
            Toast.makeText(this, getString(R.string.register_fingerprint), Toast.LENGTH_LONG).show()
            return
        }

        interactor.createKey("default_key")
    }
}
