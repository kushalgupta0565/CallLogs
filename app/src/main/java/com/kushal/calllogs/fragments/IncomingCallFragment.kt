package com.kushal.calllogs.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.kushal.calllogs.R
import com.kushal.calllogs.adapter.CallLogsAdapter
import com.kushal.calllogs.utils.CallLogsUtils

class IncomingCallFragment : Fragment() {

    lateinit var rv_incoming_logs: RecyclerView
    private var logsRunnable: Runnable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.frag_incoming, container, false)
        initializeViews(view)
        return view
    }

    private fun initializeViews(view: View) {
        rv_incoming_logs = view.findViewById(R.id.rv_incoming_logs)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logsRunnable = Runnable { loadIncomingLogs() }
        logsRunnable!!.run()
    }

    private fun loadIncomingLogs() {
        val callLogs = CallLogsUtils(context!!).getIncomingCallLogs()
        rv_incoming_logs.adapter = CallLogsAdapter(callLogs)

    }

}