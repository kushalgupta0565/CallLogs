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

class AllCallsFragment : Fragment() {

    lateinit var rv_all_calls_logs: RecyclerView
    private var logsRunnable: Runnable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.frag_all_calls, container, false)
        initializeViews(view)
        return view
    }

    private fun initializeViews(view: View) {
        rv_all_calls_logs = view.findViewById(R.id.rv_all_calls_logs)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logsRunnable = Runnable { loadIncomingLogs() }
        logsRunnable!!.run()
    }

    private fun loadIncomingLogs() {
        val callLogs = CallLogsUtils(context!!).getAllCallLogs()
        rv_all_calls_logs.adapter = CallLogsAdapter(callLogs)
    }
}