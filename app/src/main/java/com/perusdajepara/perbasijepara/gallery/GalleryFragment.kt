package com.perusdajepara.perbasijepara.gallery


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.perusdajepara.perbasijepara.R
import com.perusdajepara.perbasijepara.adapter.TabAdapter
import com.perusdajepara.perbasijepara.gallery.foto.FotoFragment
import com.perusdajepara.perbasijepara.gallery.video.VideoFragment
import kotlinx.android.synthetic.main.fragment_gallery.*

class GalleryFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val infoTabAdapter = TabAdapter(childFragmentManager)
        infoTabAdapter.addFragment(getString(R.string.foto), FotoFragment())
        infoTabAdapter.addFragment(getString(R.string.video), VideoFragment())

        gallery_pager.adapter = infoTabAdapter
        gallery_tab.setupWithViewPager(gallery_pager)
    }

}
