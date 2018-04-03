package parth.mfa_fingerprint.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.transition.Slide
import android.view.Gravity
import android.view.View
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
        setSupportActionBar(findViewById(R.id.toolbar))
        setupWindowAnimations()
        /* Create the Presenter and Interactor */
        interactor = FingerprintInteractor()
        presenter = FingerprintPresenter(this, interactor)
        // Start the authentication process
        presenter.setupCryto()
        presenter.checkForFingerprints(applicationContext)
        presenter.initCipher("default_key")
        // Listen for a fingerprint on the sensor
        presenter.startListening(this)
    }

    override fun setupWindowAnimations() {
        val slide = Slide()
        slide.duration = 100
        slide.slideEdge = Gravity.LEFT
        window.enterTransition = slide
    }

    override fun success() {
        fingerprintIcon.visibility = View.INVISIBLE
        successIcon.visibility = View.VISIBLE
        fingerprintSideText.text = "Fingerprint authenticated!"
        onResult(true)
    }

    override fun onResult(boolean: Boolean) {
        val intent = Intent()
        intent.putExtra("result", boolean)
        setResult(Activity.RESULT_OK, intent)
        finishAfterTransition()
    }
}
