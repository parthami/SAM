package parth.mfa_fingerprint.activities

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_authentication_log.*
import parth.mfa_fingerprint.R
import parth.mfa_fingerprint.adapters.LogAdapter
import parth.mfa_fingerprint.helpers.SpacesItemDecoration
import parth.mfa_fingerprint.room.AppDatabase

class AuthenticationLogActivity : AppCompatActivity() {

    private val db = AppDatabase.getAppDatabase(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication_log)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = "Authentication Log"
        // Load up first time for settings
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)

        logRecycler.setHasFixedSize(true)
        setLogAdapter()
        // Set decorations
        val decoration = SpacesItemDecoration(16)
        logRecycler.addItemDecoration(decoration)

        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        logRecycler.addItemDecoration(itemDecoration)

        logRecycler.layoutManager = LinearLayoutManager(this)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.settings, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.factors -> {
            val intent = Intent(this, SensorActivity::class.java)
            startActivity(intent)
            true
        }
        R.id.clearLogs -> {
            db.authenticationNodeLogDAO().deleteAllLogs()
            setLogAdapter()
            runLayoutAnimation(logRecycler)
            true
        }
        R.id.settings -> {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun runLayoutAnimation(recyclerView: RecyclerView) {
        val context = recyclerView.context
        val controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)

        recyclerView.layoutAnimation = controller
        recyclerView.adapter.notifyDataSetChanged()
        recyclerView.scheduleLayoutAnimation()
    }

    private fun setLogAdapter(){
        val logs = db.authenticationNodeLogDAO().getAllLogs()
        val adapter = LogAdapter(this, logs)
        logRecycler.adapter = adapter
    }
}