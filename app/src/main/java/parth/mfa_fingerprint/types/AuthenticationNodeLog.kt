package parth.mfa_fingerprint.types

import android.annotation.TargetApi
import java.util.*


/**
 * Created by Parth Chandratreya on 12/01/2018.
 */
class AuthenticationNodeLog (var id: Int, var label: String, var result: Boolean) {
}

object generateor {
    @TargetApi(26)
    fun createExampleLogs(count: Int): ArrayList<AuthenticationNodeLog> {
        var logs = ArrayList<AuthenticationNodeLog>()

        for (i in 1..count) {
            logs.add(AuthenticationNodeLog(count, "Fingerprint", Random().nextBoolean()))
        }
        return logs
    }
}
