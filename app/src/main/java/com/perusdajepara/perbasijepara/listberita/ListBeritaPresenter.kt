package com.perusdajepara.perbasijepara.listberita

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.perusdajepara.perbasijepara.basecontract.BasePresenter
import com.perusdajepara.perbasijepara.model.BeritaModel

class ListBeritaPresenter: BasePresenter<ListBeritaView> {

    private var mView: ListBeritaView? = null

    override fun onAttach(view: ListBeritaView) {
        mView = view
    }

    override fun onDetach() {
        mView = null
    }

    fun showListBerita() {

        mView?.showLoading()

        val query = FirebaseDatabase.getInstance().reference.child("berita")
        val options = FirebaseRecyclerOptions.Builder<BeritaModel>()
                .setQuery(query, BeritaModel::class.java).build()

        mView?.showList(options)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                mView?.errorCancelled()
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (!p0.exists()) mView?.dataEmpty() else mView?.dataNotEmpty()
                mView?.hideLoading()
            }

        })
    }
}