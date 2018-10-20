package com.perusdajepara.perbasijepara.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class TabAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

    private val fragmentTab: ArrayList<Fragment> = ArrayList()
    private val titleTab: ArrayList<String> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return fragmentTab[position]
    }

    override fun getCount(): Int {
        return fragmentTab.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleTab[position]
    }

    fun addFragment(title: String, fragment: Fragment) {
        fragmentTab.add(fragment)
        titleTab.add(title)
    }
}