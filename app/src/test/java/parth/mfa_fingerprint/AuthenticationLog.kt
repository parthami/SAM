package parth.mfa_fingerprint

import parth.mfa_fingerprint.activities.AuthenticationLogActivity
import parth.mfa_fingerprint.room.AppDatabase

/**
 * Created by Parth Chandratreya on 27/01/2018.
 */
class AuthenticationLog {

    private val authenticationLogActivity = AuthenticationLogActivity()
    private val db = AppDatabase.getAppDatabase(authenticationLogActivity)
}