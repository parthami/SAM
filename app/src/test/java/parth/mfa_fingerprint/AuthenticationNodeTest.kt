package parth.mfa_fingerprint

import org.junit.Assert
import org.junit.Test
import parth.mfa_fingerprint.types.AuthenticationNode

/**
 * Created by Parth Chandratreya on 06/02/2018.
 */
class AuthenticationNodeTest {

    @Test
    fun checkName(){
        val node = AuthenticationNode.FINGERPRINT
        Assert.assertEquals("Fingerprint", node.label)
    }
}