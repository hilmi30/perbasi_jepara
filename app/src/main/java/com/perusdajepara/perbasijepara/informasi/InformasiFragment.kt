package com.perusdajepara.perbasijepara.informasi


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.perusdajepara.perbasijepara.R
import com.perusdajepara.perbasijepara.adapter.TabAdapter
import com.perusdajepara.perbasijepara.listberita.ListBeritaFragment
import com.perusdajepara.perbasijepara.listevent.ListEventFragment
import kotlinx.android.synthetic.main.fragment_informasi.*

class InformasiFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_informasi, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val infoTabAdapter = TabAdapter(childFragmentManager)
        infoTabAdapter.addFragment(getString(R.string.berita), ListBeritaFragment())
        infoTabAdapter.addFragment(getString(R.string.event), ListEventFragment())

        info_pager.adapter = infoTabAdapter
        info_tab.setupWithViewPager(info_pager)
    }
}
