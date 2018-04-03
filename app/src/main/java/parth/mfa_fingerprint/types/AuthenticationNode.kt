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
    ONETIME("OneTime", Enviroment.NONE, 0.14, true);

    companion object {
        val upper_limit = 0.5
        val lower_limit = 0.05

        fun changeProbability(aNode: AuthenticationNode, b: Boolean) {

            val limit = values().map { it.probability }
            if (limit.contains(upper_limit) || limit.contains(lower_limit)) {
                reset()
            } else {
                aNode.probability += 0.05
            }
        }

        fun setEnabled(a: AuthenticationNode, b : Boolean) {
            a.enabled = b
        }

        private fun reset(){
            values().map { it.probability = 0.14 }
        }
    }
}


