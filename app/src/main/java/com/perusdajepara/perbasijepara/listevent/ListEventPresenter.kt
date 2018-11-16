package com.perusdajepara.perbasijepara.listevent

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.perusdajepara.perbasijepara.basecontract.BasePresenter
import com.perusdajepara.perbasijepara.model.EventModel

class ListEventPresenter: BasePresenter<ListEventView> {

    var mView: ListEventView? = null

    override fun onAttach(view: ListEventView) {
        mView = view
    }

    override fun onDetach() {
        mView = null
    }

    fun showListEvent() {

        mView?.showLoading()

        val query = FirebaseDatabase.getInstance().reference.child("event")
        query.keepSynced(true)
        val options = FirebaseRecyclerOptions.Builder<EventModel>()
                .setQuery(query, EventModel::class.java).build()

        mView?.showList(options)

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