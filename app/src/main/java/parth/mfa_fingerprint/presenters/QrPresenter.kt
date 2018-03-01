package parth.mfa_fingerprint.presenters

import android.util.Log
import android.widget.ImageView
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.BarcodeEncoder
import parth.mfa_fingerprint.activities.QrActivity
import parth.mfa_fingerprint.helpers.Base64
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
        val converted = Base64.encodeBytes(mac)
        Log.i("PTAG", "Generate mac: $converted")
    }

    override fun generateQRCode(imageView: ImageView) {
        val multiFormatWriter = MultiFormatWriter()
        try {
            val bitMatrix = multiFormatWriter.encode(Base64.encodeBytes(mac), BarcodeFormat.QR_CODE, 400, 400)
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

    override fun decryptMAC(identifier: String, encryptedMAC : String): Boolean {
        return interactor.compareMACs(identifier, encryptedMAC)
    }

    fun generateKey (){
        interactor.generateKey()
    }
}