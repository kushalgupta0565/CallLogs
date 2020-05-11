package com.kushal.calllogs.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.kushal.calllogs.R
import com.kushal.calllogs.adapter.CallLogsAdapter
import com.kushal.calllogs.model.CallLogData
import com.kushal.calllogs.utils.CallLogsUtils
import rx.Observable
import rx.Scheduler
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.ArrayList

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

        val observable = Observable<ArrayList<CallLogData>>()
        observable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : Subscriber<ArrayList<CallLogData>>() {
                override fun onNext(dataList: ArrayList<CallLogData>) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onCompleted() {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onError(e: Throwable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            })


        logsRunnable = Runnable { loadAllLogs() }
        logsRunnable!!.run()
    }

    private fun loadAllLogs() {
        val callLogs = CallLogsUtils(context!!).getAllCallLogs()
        rv_all_calls_logs.adapter = CallLogsAdapter(callLogs)
    }
}