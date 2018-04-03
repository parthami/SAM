package parth.mfa_fingerprint

import org.junit.Test
import parth.mfa_fingerprint.helpers.AliasMethod
import java.util.*

/**
 * Created by Parth Chandratreya on 19/03/2018.
 */
class AliasMethodTest {

    val probabilities: ArrayList<Double> = arrayListOf(0.7,0.2,0.1)
    val oneResultArray: ArrayList<Double> = arrayListOf(0.0,0.0,0.0,1.0,0.0,0.0,0.0,0.0,0.0)
    val random : Random = Random()

    @Test
    fun expectedResult(){
        val aliasMethod = AliasMethod(oneResultArray)
        val i = aliasMethod.generation()
        assert(i == 3)
    }

    @Test
    fun multipleResults() {
        val aliasMethod = AliasMethod(probabilities)
        val results = arrayOf(0,0,0)
        for (i in 0..999) {
            val a = aliasMethod.generation()
            results[a] = results[a] + 1
        }
        print("array: ")
        for(i in 0..2){
            print("${results[i]},")
        }
        assert(results[0] > 600)
    }
}