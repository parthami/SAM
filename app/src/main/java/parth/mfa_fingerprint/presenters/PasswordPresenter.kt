package parth.mfa_fingerprint.presenters

import android.text.Editable
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

    private lateinit var passwordToEncrypt: CharArray
    private lateinit var passwordToCheck: CharArray
    private val iterationCount = 1000
    private val keyLength = 256
    private val saltLength = keyLength / 8

    override fun hashPassword(editable: Editable) {
        passwordToEncrypt = editable.toString().toCharArray()
        val random = SecureRandom()
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        // Generate the salt
        val salt = ByteArray(saltLength)
        random.nextBytes(salt)
        //  Generate  PBEKey instance and a key
        val keySpec = PBEKeySpec(passwordToEncrypt, salt, iterationCount, keyLength)
        val keyFactory = SecretKeyFactory.getInstance("PBKDF2withHmacSHA1")
        val keyBytes = keyFactory.generateSecret(keySpec).encoded
        val key = SecretKeySpec(keyBytes, "AES")
        // Generate IV
        val iv = ByteArray(cipher.blockSize)
        random.nextBytes(iv)
        val ivParams = IvParameterSpec(iv)
        // Initiate the cipher
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParams)
        val encryptedPassword = cipher.doFinal(passwordToEncrypt.toString().toByteArray())
        savePassword(encryptedPassword,salt, iv)
    }

    override fun savePassword(encryptedPassword: ByteArray, salt: ByteArray, iv: ByteArray) {
        interactor.savePassword(encryptedPassword, salt,iv)
    }

    override fun comparePassword(editable: Editable): Boolean {
        passwordToCheck = editable.toString().toCharArray()
        val passwordItems: Array<String> = interactor.loadPassword()
        val encryptedPasswordHash = Base64.decode(passwordItems[0])
        val salt = Base64.decode(passwordItems[1])
        val iv = Base64.decode(passwordItems[2])

        val ivParams = IvParameterSpec(iv)
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")

        val keySpec = PBEKeySpec(passwordToCheck, salt, iterationCount, keyLength)
        val keyFactory = SecretKeyFactory.getInstance("PBKDF2withHmacSHA1")
        val keyBytes = keyFactory.generateSecret(keySpec).encoded
        val key = SecretKeySpec(keyBytes, "AES")

        cipher.init(Cipher.ENCRYPT_MODE, key, ivParams)
        val passwordToCheckHash = cipher.doFinal(passwordToCheck.toString().toByteArray())
        return passwordToCheckHash.contentEquals(encryptedPasswordHash)
    }
}