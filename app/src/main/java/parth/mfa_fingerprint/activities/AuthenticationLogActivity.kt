package parth.mfa_fingerprint.activities

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_authentication_log_actvity.*
import parth.mfa_fingerprint.R
import parth.mfa_fingerprint.adapters.LogAdapter
import parth.mfa_fingerprint.helpers.SpacesItemDecoration
import parth.mfa_fingerprint.types.AuthenticationNodeLog
import parth.mfa_fingerprint.types.generateor





class AuthenticationLogActivity : Activity() {

    var logs = ArrayList<AuthenticationNodeLog>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication_log_actvity)

        var logs = generateor.createExampleLogs(50)
        var adapter = LogAdapter(this, logs)
        logRecycler.adapter = adapter

        val decoration = SpacesItemDecoration(16)
        logRecycler.addItemDecoration(decoration)

        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        logRecycler.addItemDecoration(itemDecoration)

        logRecycler.layoutManager = LinearLayoutManager(this)
    }
}
