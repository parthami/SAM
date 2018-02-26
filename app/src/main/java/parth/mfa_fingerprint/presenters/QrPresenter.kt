package parth.mfa_fingerprint.presenters

import parth.mfa_fingerprint.interactors.QrInteractor
import parth.mfa_fingerprint.interfaces.QrPresenterI
import parth.mfa_fingerprint.interfaces.QrView

/**
 * Created by Parth Chandratreya on 24/02/2018.
 */
class QrPresenter (val view : QrView, val interactor : QrInteractor ) : QrPresenterI {
}