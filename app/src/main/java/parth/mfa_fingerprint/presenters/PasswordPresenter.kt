package parth.mfa_fingerprint.presenters

import android.text.Editable
import android.util.Log
import parth.mfa_fingerprint.helpers.Base64
import parth.mfa_fingerprint.interactors.PasswordInteractor
import parth.mfa_fingerprint.interfaces.PasswordPresenterI
import parth.mfa_fingerprint.interfaces.PasswordView
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

/**
 * Created by Parth Chandratreya on 22/01/2018.
 */
class PasswordPresenter(val view: PasswordView, private val interactor: PasswordInteractor) : PasswordPresenterI {

    private val iterationCount = 1000
    private val keyLength = 256
    private val saltLength = keyLength / 8
    private val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
    private val keyFactory = SecretKeyFactory.getInstance("PBKDF2withHmacSHA1")

    override fun hashPassword(passwordToEncrypt: String) {
        val random = SecureRandom()
        // Generate the salt
        val salt = ByteArray(saltLength)
        random.nextBytes(salt)
        // Generate IV
        val iv = ByteArray(cipher.blockSize)
        random.nextBytes(iv)
        val ivParams = IvParameterSpec(iv)
        // Create the password hash
        val encryptedPassword = hashGeneration(passwordToEncrypt, salt, ivParams)
        savePassword(encryptedPassword, salt, iv)
    }

    override fun savePassword(encryptedPassword: ByteArray, salt: ByteArray, iv: ByteArray) {
        interactor.savePassword(encryptedPassword, salt, iv)
    }

    override fun comparePassword(editable: Editable): Boolean {
        val passwordToCheck = editable.toString()
        // Load password items
        val passwordItems: Array<String> = interactor.loadPassword()
        val savedPassword = Base64.decode(passwordItems[0])
        val salt = Base64.decode(passwordItems[1])
        val iv = Base64.decode(passwordItems[2])

        val ivParams = IvParameterSpec(iv)
        val encryptedPassword = hashGeneration(passwordToCheck, salt, ivParams)
        val check = encryptedPassword.contentEquals(savedPassword)
        Log.i("PTAG", "Comparing hashes  - $check")
        return check
    }


    override fun hashGeneration(passwordToEncrypt: String, salt: ByteArray, ivParams: IvParameterSpec): ByteArray {
        //  Generate  PBEKey instance and a key
        val keySpec = PBEKeySpec(passwordToEncrypt.toCharArray(), salt, iterationCount, keyLength)
        val keyBytes = keyFactory.generateSecret(keySpec).encoded
        val key = SecretKeySpec(keyBytes, "AES")
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParams)
        return cipher.doFinal(passwordToEncrypt.toByteArray())
    }
}