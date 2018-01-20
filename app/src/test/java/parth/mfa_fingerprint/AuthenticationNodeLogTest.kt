package parth.mfa_fingerprint

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import parth.mfa_fingerprint.room.AuthenticationNodeLog
import parth.mfa_fingerprint.types.Generator
import java.util.*

/**
 * Created by Parth Chandratreya on 18/01/2018.
 */
class AuthenticationNodeLogTest {

    lateinit var logs : ArrayList<AuthenticationNodeLog>

    @Before
    fun before(){
        logs = Generator.createExampleLogs(50)
    }


    @Test
    fun createExampleLogs_notEmpty(){
        Assert.assertFalse(logs.isEmpty())
    }

    @Test
    fun createExampleLogs_checkElement(){
        val random = Random().nextInt(50)
        Assert.assertNotNull(logs[random].label)
    }

}