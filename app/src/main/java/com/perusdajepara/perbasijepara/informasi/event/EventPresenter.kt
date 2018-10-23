package com.perusdajepara.perbasijepara.informasi.event

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.perusdajepara.perbasijepara.basecontract.BasePresenter

class EventPresenter: BasePresenter<EventView> {

    var mView: EventView? = null

    override fun onAttach(view: EventView) {
        mView = view
    }

    override fun onDetach() {
        mView = null
    }

    fun showListEvent() {
        mView?.showLoading()
        val mDatabase = FirebaseDatabase.getInstance().reference
        val query = mDatabase.child("event")
        val options = FirebaseRecyclerOptions.Builder<EventModel>()
                .setQuery(query, EventModel::class.java).build()

        mView?.showList(options)
    }
}