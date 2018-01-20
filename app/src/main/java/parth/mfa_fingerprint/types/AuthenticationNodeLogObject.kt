package parth.mfa_fingerprint.types

import android.annotation.TargetApi
import java.util.*

/**
 * Created by Parth Chandratreya on 12/01/2018.
 */
class AuthenticationNodeLogObject (var id: Int, var label: String, var result: Boolean, var dateTime: Long) {
}

object Generator {
    @TargetApi(26)
    fun createExampleLogs(count: Int): ArrayList<AuthenticationNodeLogObject> {
        val logs = ArrayList<AuthenticationNodeLogObject>()
        for (i in 1..count) {
            logs.add(AuthenticationNodeLogObject(count, "Fingerprint", Random().nextBoolean(), Date().time))
        }
        return logs
    }
}
