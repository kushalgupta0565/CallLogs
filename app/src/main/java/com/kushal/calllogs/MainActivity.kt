package com.kushal.calllogs

import android.Manifest
import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kushal.calllogs.adapter.ViewpagerFragmentStateAdapter
import com.kushal.calllogs.callbacks.IPermissionCallback
import com.kushal.calllogs.fragments.AllCallsFragment
import com.kushal.calllogs.fragments.IncomingCallFragment
import com.kushal.calllogs.fragments.MissedCallFragment
import com.kushal.calllogs.fragments.OutgoingCallFragment
import com.kushal.calllogs.utils.PermissionUtils

class MainActivity : FragmentActivity(), IPermissionCallback {

    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager2
    val tabStrArr = arrayOf("All Calls", "Missed", "Incoming", "Outgoing")
    var fragArrList: ArrayList<Fragment>? = null

    companion object {
        val READ_LOGS = 725
        val requiredPermissions = arrayOf(
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.CALL_PHONE
        )
    }
    var permissionUtils: PermissionUtils? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeViews()

        permissionUtils = PermissionUtils(this, this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissionUtils!!.checkPermissionToExecute(
                requiredPermissions,
                READ_LOGS
            )
        } else {
            showFragmentsWithTabs()
        }
    }

    private fun initializeViews() {
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.view_pager)
    }

    // Sample Fragments
    private fun showFragmentsWithTabs() {
        fragArrList = ArrayList()
        fragArrList!!.add(AllCallsFragment())
        fragArrList!!.add(MissedCallFragment())
        fragArrList!!.add(IncomingCallFragment())
        fragArrList!!.add(OutgoingCallFragment())

        viewPager.adapter =
            ViewpagerFragmentStateAdapter(
                this,
                fragArrList!!
            )
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabStrArr[position]
        }.attach()
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }

    // Permissions
    @TargetApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionUtils!!.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onAllPermissionGranted() {
        showFragmentsWithTabs()
    }

    override fun onRetry() {
        permissionUtils!!.checkPermissionToExecute(requiredPermissions, READ_LOGS)
    }

    override fun onExit() {
        finish()
    }
}
