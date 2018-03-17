package parth.mfa_fingerprint.activities

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.transition.Slide
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.View.VISIBLE
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_qr.*
import parth.mfa_fingerprint.R
import parth.mfa_fingerprint.interactors.QrInteractor
import parth.mfa_fingerprint.interfaces.QrView
import parth.mfa_fingerprint.presenters.QrPresenter
import java.text.SimpleDateFormat
import java.util.*




class QrActivity : AppCompatActivity(), QrView {
    private lateinit var interactor: QrInteractor
    private lateinit var presenter: QrPresenter
    private val identifier : String = "finalYearProject"
    private lateinit var encrptyedMAC : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr)
        setSupportActionBar(findViewById(R.id.toolbar))
        setupWindowAnimations()

        interactor = QrInteractor()
        presenter = QrPresenter(this, interactor)
//        createMAC()

        floatingActionButton.setOnClickListener({
//            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val timestamp = SimpleDateFormat("dd-MM-yy", Locale.UK).format(Date())
            val bd  =  qrCodeImage.drawable as BitmapDrawable
            // TODO add file details
            MediaStore.Images.Media.insertImage(contentResolver, bd.bitmap,timestamp, "qrCode")
            Snackbar.make(coordinatorLayout, "Saved", Snackbar.LENGTH_SHORT).show()
        })

    }

    override fun launchCamera(view : View) {
        presenter.scanQRCode(this)
    }

    override fun createMAC() {
//        TODO add actual completion result
        Log.i("PTAG", "Initial identifier: $identifier")
        presenter.generateMAC(identifier)
        button5.isEnabled = true
    }

    override fun createQR(view: View) {
        createMAC()
        presenter.generateQRCode(qrCodeImage)
        floatingActionButton.visibility = VISIBLE
    }

    override fun authenticate(v : View) {
        val auth : Boolean = presenter.decryptMAC(identifier, encrptyedMAC)
        scannedText.text = auth.toString()
        Snackbar.make(coordinatorLayout, "$auth", Snackbar.LENGTH_SHORT).show()
        onResult(auth)
    }

    fun generateKey (v : View){
        presenter.generateKey()
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
                Log.i("PTAG", "Scanned : ${result.contents}")
//                scannedText.text  =  result.contents
                encrptyedMAC = result.contents
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

    fun onResult(boolean: Boolean) {
        val intent = Intent()
        intent.putExtra("result", boolean)
        setResult(Activity.RESULT_OK, intent)
        finishAfterTransition()
    }
}
