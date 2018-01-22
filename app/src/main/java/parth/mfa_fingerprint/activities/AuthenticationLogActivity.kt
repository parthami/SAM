package parth.mfa_fingerprint.activities

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_authentication_log.*
import parth.mfa_fingerprint.R
import parth.mfa_fingerprint.adapters.LogAdapter
import parth.mfa_fingerprint.helpers.SpacesItemDecoration
import parth.mfa_fingerprint.room.AppDatabase


class AuthenticationLogActivity : Activity() {

//    var logs = ArrayList<AuthenticationNodeLog>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication_log)
        val db = AppDatabase.getAppDatabase(this)

//        val logs = Generator.createExampleLogs(50)
        val logs = db.authenticationNodeLogDAO().getAllTasks()
        val adapter = LogAdapter(this, logs)
        logRecycler.adapter = adapter

        val decoration = SpacesItemDecoration(16)
        logRecycler.addItemDecoration(decoration)

        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        logRecycler.addItemDecoration(itemDecoration)

        val animation = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_fall_down)
        logRecycler.layoutAnimation = animation

        logRecycler.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu) : Boolean    {
        menuInflater.inflate(R.menu.settings, menu)
        return super.onCreateOptionsMenu(menu)
    }
}