package parth.mfa_fingerprint.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import parth.mfa_fingerprint.R

class BlankActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blank)
    }

    fun onClick(v : View) {
        onResult(true)
    }

    fun onResult(boolean: Boolean) {
        val intent = Intent()
        intent.putExtra("result", boolean)
        setResult(Activity.RESULT_OK, intent)
        finishAfterTransition()
    }
}
