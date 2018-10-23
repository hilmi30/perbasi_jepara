package com.perusdajepara.perbasijepara.informasi.event.kategori

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.perusdajepara.perbasijepara.basecontract.BasePresenter

class KategoriEventPresenter: BasePresenter<KategoriEventView> {

    private var mView: KategoriEventView? = null

    override fun onAttach(view: KategoriEventView) {
        mView = view
    }

    override fun onDetach() {
        mView = null
    }

    fun showListKategoriEvent(key: String) {
        val query = FirebaseDatabase.getInstance().reference
                .child("kategoriEvent").child(key)
        val options = FirebaseRecyclerOptions
                .Builder<KategoriEventModel>()
                .setQuery(query, KategoriEventModel::class.java).build()

        mView?.showEventKategori(options)
    }
}