package parth.mfa_fingerprint.types

/**
 * Created by Parth Chandratreya on 12/01/2018.
 */

//affectedBy
//0 = none
//1 = light
//2 = motion
//3 = sound

enum class AuthenticationNode(val label: String, val affectedBy : Enviroment, var enabled : Boolean) {
    FINGERPRINT("Fingerprint",Enviroment.NONE, true),
    QR("QR Code",Enviroment.LIGHT, true),
    PASSWORD("Password",Enviroment.NONE, true),
    BLANK("Blank",Enviroment.SOUND, true)
}