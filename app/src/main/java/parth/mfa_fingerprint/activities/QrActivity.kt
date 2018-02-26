package parth.mfa_fingerprint.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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

        interactor = QrInteractor()
        presenter = QrPresenter(this, interactor)
    }
}
