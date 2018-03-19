package parth.mfa_fingerprint.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_voice.*
import parth.mfa_fingerprint.R

class VoiceActivity : AppCompatActivity() {

//    val speakerVerification = SpeakerVerificationRestClient("e0c3704ecfed442282b5232bf52d9cc5")
//    val locale = "en-US"
//    lateinit var  profileId  : UUID
//    lateinit var  phrase : VerificationPhrase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice)
        setSupportActionBar(findViewById(R.id.toolbar))

//        Create a Profile
//        Enrol them
//        Verify

//        createProfile()
//        getPhrase()


    }

    fun onClick(v: View) {
        if(v.id == voiceSuccessButton.id) onResult(true) else onResult(false)
    }

    fun onResult(boolean: Boolean) {
        val intent = Intent()
        intent.putExtra("result", boolean)
        setResult(Activity.RESULT_OK, intent)
        finishAfterTransition()
    }

//    fun getPhrase() {
//        val phrases = speakerVerification.getPhrases(locale)
//        val random = Random()
//        phrase = phrases[random.nextInt(phrases.size)]
//        Log.i("PTAG", "phrase = $phrase")
//    }
//
//    fun createProfile(){
//         profileId = speakerVerification.createProfile(locale).verificationProfileId
//        Log.i("PTAG", "profileId = $profileId")
//    }
}
