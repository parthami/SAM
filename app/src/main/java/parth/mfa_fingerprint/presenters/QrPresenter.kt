package parth.mfa_fingerprint.presenters

import android.widget.ImageView
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.BarcodeEncoder
import parth.mfa_fingerprint.activities.QrActivity
import parth.mfa_fingerprint.interactors.QrInteractor
import parth.mfa_fingerprint.interfaces.QrPresenterI
import parth.mfa_fingerprint.interfaces.QrView


/**
 * Created by Parth Chandratreya on 24/02/2018.
 */
class QrPresenter (val view : QrView, val interactor : QrInteractor ) : QrPresenterI {
    lateinit var mac : ByteArray

    override fun generateMAC(string : String) {
        mac = interactor.encryptMAC(string)
    }

    override fun generateQRCode(imageView: ImageView) {
        val multiFormatWriter = MultiFormatWriter()
        try {
            val bitMatrix = multiFormatWriter.encode(mac.toString(), BarcodeFormat.QR_CODE, 200, 200)
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.createBitmap(bitMatrix)
            imageView.setImageBitmap(bitmap)
        }   catch (e : WriterException) {
            e.printStackTrace()
        }
    }

    override fun scanQRCode(activity: QrActivity) {
            val integrator = IntentIntegrator(activity)
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
            integrator.setPrompt("Scan")
            integrator.setCameraId(0)
            integrator.setBeepEnabled(false)
            integrator.setBarcodeImageEnabled(false)
            integrator.initiateScan()
    }

    override fun decryptMAC() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}