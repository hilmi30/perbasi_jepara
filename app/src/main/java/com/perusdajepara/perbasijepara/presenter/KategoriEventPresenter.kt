package com.perusdajepara.perbasijepara.presenter

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.perusdajepara.perbasijepara.basecontract.BasePresenter
import com.perusdajepara.perbasijepara.model.KategoriEventModel
import com.perusdajepara.perbasijepara.view.KategoriEventView

class KategoriEventPresenter: BasePresenter<KategoriEventView> {

    var mView: KategoriEventView? = null

    override fun onAttach(view: KategoriEventView) {
        mView = view
    }

    override fun onDetach() {
        mView = null
    }

    fun showListKategoriEvent(key: String) {
        val query = FirebaseDatabase.getInstance().reference.child(key)
        val options = FirebaseRecyclerOptions
                .Builder<KategoriEventModel>().setQuery(query, KategoriEventModel::class.java).build()

        mView?.showEventKategori(options)
    }
}