package com.perusdajepara.perbasijepara.listkategori

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.perusdajepara.perbasijepara.basecontract.BasePresenter
import com.perusdajepara.perbasijepara.model.KategoriEventModel

class ListKategoriEventPresenter: BasePresenter<ListKategoriEventView> {

    private var mView: ListKategoriEventView? = null

    override fun onAttach(view: ListKategoriEventView) {
        mView = view
    }

    override fun onDetach() {
        mView = null
    }

    fun showListKategoriEvent(key: String) {

        mView?.showLoading()

        val query = FirebaseDatabase.getInstance().reference
                .child("kategoriEvent").child(key)
        val options = FirebaseRecyclerOptions
                .Builder<KategoriEventModel>()
                .setQuery(query, KategoriEventModel::class.java).build()

        mView?.showEventKategori(options)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                mView?.errorCancelled()
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(!p0.exists()) mView?.dataEmpty() else mView?.dataNotEmpty()
                mView?.hideLoading()
            }

        })
    }
}