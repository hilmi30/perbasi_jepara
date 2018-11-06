package com.perusdajepara.perbasijepara.listberita


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerOptions

import com.perusdajepara.perbasijepara.R
import com.perusdajepara.perbasijepara.detailberita.DetailBeritaActivity
import com.perusdajepara.perbasijepara.model.BeritaModel
import com.perusdajepara.perbasijepara.utils.gone
import com.perusdajepara.perbasijepara.utils.visible
import kotlinx.android.synthetic.main.fragment_berita.*
import kotlinx.android.synthetic.main.layout_data_kosong.*
import kotlinx.android.synthetic.main.layout_loading.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class ListBeritaFragment : Fragment(), ListBeritaView {

    private lateinit var presenter: ListBeritaPresenter
    private lateinit var beritaAdapter: ListBeritaRecyAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_berita, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = ListBeritaPresenter()
        onAttachView()
    }

    // ============== Setting Area

    override fun onAttachView() {
        presenter.onAttach(this)
        presenter.showListBerita()
    }

    override fun showList(options: FirebaseRecyclerOptions<BeritaModel>) {
        beritaAdapter = ListBeritaRecyAdapter(options) {
            startActivity<DetailBeritaActivity>("uid" to it)
        }
        val layoutManager = LinearLayoutManager(context)
        berita_recy.layoutManager = layoutManager
        berita_recy.adapter = beritaAdapter
    }

    override fun onStart() {
        super.onStart()
        beritaAdapter.startListening()
    }

    // ============== Destroy Area

    override fun onDetachView() {
        presenter.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
        onDetachView()
    }

    override fun onStop() {
        super.onStop()
        beritaAdapter.stopListening()
    }

    // ============== Error Area

    override fun errorCancelled() {
        toast(getString(R.string.terjadi_kesalahan))
    }

    // ============== Feedback Area

    override fun hideLoading() {
        loading.gone()
    }

    override fun dataEmpty() {
        kosong.visible()
    }

    override fun dataNotEmpty() {
        kosong.gone()
    }

    override fun showLoading() {
        loading.visible()
    }
}
