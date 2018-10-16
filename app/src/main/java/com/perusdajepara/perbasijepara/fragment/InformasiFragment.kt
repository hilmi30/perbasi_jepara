package com.perusdajepara.perbasijepara.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.perusdajepara.perbasijepara.R
import com.perusdajepara.perbasijepara.adapter.InformasiAdapter
import kotlinx.android.synthetic.main.fragment_informasi.*

class InformasiFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_informasi, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val infoTabAdapter = InformasiAdapter(childFragmentManager)
        infoTabAdapter.addFragment(getString(R.string.berita), BeritaFragment())
        infoTabAdapter.addFragment(getString(R.string.event), EventFragment())

        info_pager.adapter = infoTabAdapter
        info_tab.setupWithViewPager(info_pager)
    }
}
