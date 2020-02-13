package com.kushal.calllogs.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.util.*

class ViewpagerFragmentStateAdapter(fragmentActivity: FragmentActivity, var fragArrList: ArrayList<Fragment>) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return fragArrList[position]
    }

    override fun getItemCount(): Int {
        return fragArrList.size
    }
}