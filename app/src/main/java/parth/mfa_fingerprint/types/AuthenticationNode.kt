package parth.mfa_fingerprint.types

/**
 * Created by Parth Chandratreya on 12/01/2018.
 */

enum class AuthenticationNode(val label: String, val affectedBy : Enviroment, var probability: Double, var enabled : Boolean) {
    FINGERPRINT("Fingerprint",Enviroment.NONE, 0.14, true),
    QR("QR",Enviroment.LIGHT, 0.14, true),
    PASSWORD("Password",Enviroment.NONE, 0.14, true),
    BLANK("Blank",Enviroment.SOUND, 0.14, true),
    LOCATION("Location", Enviroment.NONE, 0.14, true),
    VOICE("Voice", Enviroment.SOUND, 0.14, true),
    ONETIME("OneTime", Enviroment.NONE, 0.14, true)
}

fun changeProbability(aNode : AuthenticationNode , p : Double) {
    aNode.probability = p
}