package com.perusdajepara.perbasijepara.informasi.event.kategori


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerOptions

import com.perusdajepara.perbasijepara.R
import com.perusdajepara.perbasijepara.informasi.event.detailkategorievent.DetailKategoriEventActivity
import com.perusdajepara.perbasijepara.utils.visible
import kotlinx.android.synthetic.main.fragment_kategori.*
import org.jetbrains.anko.support.v4.startActivity

class KategoriEventFragment : Fragment(), KategoriEventView {

    private lateinit var presenter: KategoriEventPresenter
    private lateinit var adapter: KategoriEventRecyAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kategori, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = KategoriEventPresenter()
        onAttachView()
    }

    override fun onAttachView() {
        presenter.onAttach(this)

        val uid = arguments?.getString("eventUid").toString()
        presenter.showListKategoriEvent(uid)
    }

    override fun onDetachView() {
        presenter.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
        onDetachView()
    }

    override fun showEventKategori(options: FirebaseRecyclerOptions<KategoriEventModel>) {

        adapter = KategoriEventRecyAdapter(options, kategori_event_loading) {
            startActivity<DetailKategoriEventActivity>()
        }

        val layoutManager = LinearLayoutManager(context)
        kategori_event_recy.layoutManager = layoutManager
        kategori_event_recy.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        kategori_event_loading.visible()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}
