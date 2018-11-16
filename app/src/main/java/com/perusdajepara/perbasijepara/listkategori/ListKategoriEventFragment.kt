package com.perusdajepara.perbasijepara.listkategori


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerOptions

import com.perusdajepara.perbasijepara.R
import com.perusdajepara.perbasijepara.detailkategorievent.DetailKategoriEventActivity
import com.perusdajepara.perbasijepara.model.KategoriEventModel
import com.perusdajepara.perbasijepara.utils.gone
import com.perusdajepara.perbasijepara.utils.visible
import kotlinx.android.synthetic.main.fragment_kategori.*
import kotlinx.android.synthetic.main.layout_data_kosong.*
import kotlinx.android.synthetic.main.layout_loading.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class ListKategoriEventFragment : Fragment(), ListKategoriEventView {

    private lateinit var presenter: ListKategoriEventPresenter
    private lateinit var adapter: ListKategoriEventRecyAdapter
    private lateinit var uid: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kategori, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = ListKategoriEventPresenter()
        onAttachView()
    }

    // ================ Setting Area

    override fun showEventKategori(options: FirebaseRecyclerOptions<KategoriEventModel>) {

        adapter = ListKategoriEventRecyAdapter(options) { model: KategoriEventModel, uidKategori: String ->
            startActivity<DetailKategoriEventActivity>(
                    "uid" to uidKategori,
                    "nama" to model.nama,
                    "status" to model.status.toString()
            )
        }

        val layoutManager = LinearLayoutManager(context)
        kategori_event_recy.layoutManager = layoutManager
        kategori_event_recy.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onAttachView() {
        presenter.onAttach(this)

        uid = arguments?.getString("eventUid").toString()
        presenter.showListKategoriEvent(uid)
    }

    // ================ Destroy Area

    override fun onDetachView() {
        presenter.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
        onDetachView()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    // ================ Feedback Area

    override fun hideLoading() {
        loading.gone()
    }

    override fun showLoading() {
        loading.visible()
    }

    override fun dataNotEmpty() {
        kosong.gone()
    }

    override fun dataEmpty() {
        kosong.visible()
    }

    // ================ Error Area

    override fun errorCancelled() {
        toast(getString(R.string.terjadi_kesalahan))
    }
}
