package parth.mfa_fingerprint.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.item_log.view.*
import parth.mfa_fingerprint.R
import parth.mfa_fingerprint.helpers.FontManager
import parth.mfa_fingerprint.room.AuthenticationNodeLog


/**
 * Created by Parth Chandratreya on 13/01/2018.
 */
class LogAdapter(var context: Context, var logs: List<AuthenticationNodeLog>) : RecyclerView.Adapter<LogAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val context = parent?.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.item_log, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        // Get the data model based on position
        val log = logs.get(position)

        val label = holder?.labelView
        label?.text = log.label

        val result = holder?.resultView
        result?.text = log.result.toString().capitalize()

        val iconFont = FontManager().getTypeface(context, FontManager().fa)
        val cardIcon = holder?.cardIcon
        cardIcon?.typeface = iconFont
    }

    override fun getItemCount(): Int {
        return logs.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        var labelView: TextView? = null
        var resultView: TextView? = null
        var cardIcon: TextView? = null

        init {
            labelView = itemView.labelTextView
            resultView = itemView.resultTextView
            cardIcon = itemView.cardIcon
        }
    }


}