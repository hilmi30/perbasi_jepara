package com.perusdajepara.perbasijepara.berita


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerOptions

import com.perusdajepara.perbasijepara.R
import com.perusdajepara.perbasijepara.presenter.BeritaPresenter
import com.perusdajepara.perbasijepara.utils.visible
import com.perusdajepara.perbasijepara.view.BeritaView
import kotlinx.android.synthetic.main.fragment_berita.*
import org.jetbrains.anko.support.v4.startActivity

class BeritaFragment : Fragment(), BeritaView {

    private lateinit var presenter: BeritaPresenter
    private lateinit var beritaAdapter: BeritaRecyAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_berita, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = BeritaPresenter()
        onAttachView()
    }

    override fun onAttachView() {
        presenter.onAttach(this)
        presenter.showListBerita()
    }

    override fun onDetachView() {
        presenter.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
        onDetachView()
    }

    override fun showList(options: FirebaseRecyclerOptions<BeritaModel>) {
        beritaAdapter = BeritaRecyAdapter(options, berita_loading) {
            startActivity<BeritaDetailActivity>(
                    "createdAt" to it.createdAt,
                    "deskripsi" to it.deskripsi,
                    "judul" to it.judul,
                    "thumbnail" to it.thumbnail,
                    "updatedAt" to it.updatedAt
            )
        }
        val layoutManager = LinearLayoutManager(context)
        berita_recy.layoutManager = layoutManager
        berita_recy.adapter = beritaAdapter
    }

    override fun onStart() {
        super.onStart()
        berita_loading.visible()
        beritaAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        beritaAdapter.stopListening()
    }
}
