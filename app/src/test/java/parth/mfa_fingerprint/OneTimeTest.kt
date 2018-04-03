package parth.mfa_fingerprint

import org.junit.Before
import org.junit.Test
import parth.mfa_fingerprint.activities.OneTimeActivity
import parth.mfa_fingerprint.interactors.OneTimeInteractor
import parth.mfa_fingerprint.presenters.OneTimePresenter

/**
 * Created by Parth Chandratreya on 03/04/2018.
 */
class OneTimeTest {

    lateinit var presenter : OneTimePresenter

    @Before
    fun setup(){
        val activity = OneTimeActivity()
        val interactor = OneTimeInteractor()
        presenter = OneTimePresenter(activity,interactor)
        presenter.generatePassword(false)
    }

    @Test
    fun checkPasswordTrueTest() {
       assert(presenter.checkPassword("210796"))
    }

    @Test
    fun checkPasswordFalseTest() {
        assert(!presenter.checkPassword("11111"))
    }
}