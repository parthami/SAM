package parth.mfa_fingerprint

import org.junit.Before
import org.junit.Test
import parth.mfa_fingerprint.activities.SelectionActivity
import parth.mfa_fingerprint.types.AuthenticationNode

/**
 * Created by Parth Chandratreya on 22/03/2018.
 */
class SelectionTest {

    val lowerLimit = 0.0
    val higherLimit = 100.0
    val enabledArray = arrayOf(AuthenticationNode.FINGERPRINT)
    val disabledArray = arrayOf(AuthenticationNode.FINGERPRINT)
    val selectionActivity = SelectionActivity()

    @Before
    fun before() {
        enabledArray[0].enabled = true
        disabledArray[0].enabled = false
    }

    @Test
    fun inRangeTestInRange() {
        val value = 50.0
        val bool = selectionActivity.inLimitRange(value, lowerLimit, higherLimit)
        assert(bool)
    }

    @Test
    fun inRangeTestOutOfRange() {
        val value = 150.0
        val bool = selectionActivity.inLimitRange(value, lowerLimit, higherLimit)
        assert(!bool)
    }

//    @Test
//    fun checkEnabledFactorList(){
//        val layout : LinearLayout = LinearLayout(selectionActivity)
//        selectionActivity.addLabelsToList(enabledArray,layout, true)
//        assert(layout.childCount > 0)
//    }
//
//    @Test
//    fun checkDisabledFactorList(){
//        val layout : LinearLayout = LinearLayout(selectionActivity)
//        selectionActivity.addLabelsToList(disabledArray,layout, false)
//        assert(layout.childCount > 0)
//
//    }
}