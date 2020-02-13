package com.kushal.calllogs.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kushal.calllogs.R
import com.kushal.calllogs.model.CallLogData
import com.kushal.calllogs.utils.CallLogsUtils
import java.text.DateFormat
import java.util.*


class CallLogsAdapter(val callLogs: ArrayList<CallLogData>): RecyclerView.Adapter<CallLogsAdapter.CallLogViewHolder>() {

    val dateFormat:DateFormat
    init {
        dateFormat = DateFormat.getDateTimeInstance(
            DateFormat.ERA_FIELD,
            DateFormat.SHORT
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CallLogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_call_log, parent, false)
        return CallLogViewHolder(view)
    }

    override fun getItemCount(): Int {
        return callLogs.size
    }

    @SuppressLint("MissingPermission")
    override fun onBindViewHolder(holder: CallLogViewHolder, position: Int) {
        holder.tv_name_number.text = callLogs[position].name
        if (callLogs[position].type == CallLogsUtils.INCOMING_TYPE)
            holder.iv_call_type.setImageResource(R.drawable.ic_incoming)
        else if (callLogs[position].type == CallLogsUtils.OUTGOING_TYPE)
            holder.iv_call_type.setImageResource(R.drawable.ic_outgoing)
        else if (callLogs[position].type == CallLogsUtils.MISSED_TYPE)
            holder.iv_call_type.setImageResource(R.drawable.ic_missed)
        holder.tv_call_duration.text = callLogs[position].duration
        val date = Date(callLogs[position].date)
        holder.tv_call_date.text = dateFormat.format(date)

        holder.iv_call.setOnClickListener {
            val number = callLogs[position].number
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:$number")
            holder.iv_call.context.startActivity(callIntent)
        }
    }

    class CallLogViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tv_name_number = itemView.findViewById<TextView>(R.id.tv_name_number)
        val iv_call_type = itemView.findViewById<ImageView>(R.id.iv_call_type)
        val tv_call_duration = itemView.findViewById<TextView>(R.id.tv_call_duration)
        val tv_call_date = itemView.findViewById<TextView>(R.id.tv_call_date)
        val iv_call = itemView.findViewById<ImageView>(R.id.iv_call)
    }
}