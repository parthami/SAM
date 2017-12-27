package parth.mfa_fingerprint

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val b = R.id.toggleButton
//        b.setOnClickListener(object : View.OnClickListener() {
//            fun onClick(v: View) {
//                // your click actions go here
//            }
//        })
    }
}
