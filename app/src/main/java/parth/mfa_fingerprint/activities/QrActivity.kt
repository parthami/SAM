package parth.mfa_fingerprint.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.transition.Slide
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_qr.*
import parth.mfa_fingerprint.R
import parth.mfa_fingerprint.interactors.QrInteractor
import parth.mfa_fingerprint.interfaces.QrView
import parth.mfa_fingerprint.presenters.QrPresenter


class QrActivity : AppCompatActivity(), QrView {
    private lateinit var interactor: QrInteractor
    private lateinit var presenter: QrPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr)
        setSupportActionBar(findViewById(R.id.toolbar))
        setupWindowAnimations()

        interactor = QrInteractor()
        presenter = QrPresenter(this, interactor)

        createMAC()
    }

    override fun launchCamera(view : View) {
        presenter.scanQRCode(this)
    }

    override fun createMAC() {
//        TODO add actual completion result
        presenter.generateMAC("finalYearProject")
        button5.isEnabled = true

    }

    override fun createQR(view: View) {
        presenter.generateQRCode(imageView2)
    }

    override fun authenticate() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Log.d("MainActivity", "Cancelled scan")
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                Log.d("MainActivity", "Scanned")
                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun setupWindowAnimations() {
        val slide = Slide()
        slide.duration = 100
        slide.slideEdge = Gravity.LEFT
        window.enterTransition = slide
    }
}
