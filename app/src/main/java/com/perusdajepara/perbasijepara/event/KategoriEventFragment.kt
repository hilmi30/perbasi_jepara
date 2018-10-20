package com.perusdajepara.perbasijepara.event


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerOptions

import com.perusdajepara.perbasijepara.R
import com.perusdajepara.perbasijepara.model.KategoriEventModel
import com.perusdajepara.perbasijepara.presenter.KategoriEventPresenter
import com.perusdajepara.perbasijepara.view.KategoriEventView

class KategoriEventFragment : Fragment(), KategoriEventView {

    private lateinit var presenter: KategoriEventPresenter

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

        // presenter.showListKategoriEvent()
    }

    override fun onDetachView() {
        presenter.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
        onDetachView()
    }

    override fun showEventKategori(options: FirebaseRecyclerOptions<KategoriEventModel>) {

    }
}
