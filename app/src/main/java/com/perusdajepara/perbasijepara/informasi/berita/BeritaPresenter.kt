package com.perusdajepara.perbasijepara.informasi.berita

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.perusdajepara.perbasijepara.basecontract.BasePresenter

class BeritaPresenter: BasePresenter<BeritaView> {

    private var mView: BeritaView? = null

    override fun onAttach(view: BeritaView) {
        mView = view
    }

    override fun onDetach() {
        mView = null
    }

    fun showListBerita() {
        val mDatabase = FirebaseDatabase.getInstance().reference
        val query = mDatabase.child("berita")
        val options = FirebaseRecyclerOptions.Builder<BeritaModel>()
                .setQuery(query, BeritaModel::class.java).build()

        mView?.showList(options)
        // sembunyikan loading ada di berita adapter karena tidak ada listener success untuk firebase ui database
    }
}