package parth.mfa_fingerprint.types

import android.annotation.TargetApi
import parth.mfa_fingerprint.room.AuthenticationNodeLog
import java.util.*

/**
 * Created by Parth Chandratreya on 12/01/2018.
 */
class AuthenticationNodeLogObject (var id: Int, var label: String, var result: Boolean, var dateTime: Long) {
}

object Generator {
    @TargetApi(26)
    fun createExampleLogs(count: Int): ArrayList<AuthenticationNodeLog> {
        val logs = ArrayList<AuthenticationNodeLog>()
        for (i in 1..count) {
            logs.add(AuthenticationNodeLog("Fingerprint", Random().nextBoolean(), Date().time))
        }
        return logs
    }
}
