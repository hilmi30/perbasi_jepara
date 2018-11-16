package com.perusdajepara.perbasijepara.liststandings

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.perusdajepara.perbasijepara.basecontract.BasePresenter
import com.perusdajepara.perbasijepara.model.StandingsModel

class ListStandingsPresenter: BasePresenter<ListStandingsView> {

    var mView: ListStandingsView? = null

    override fun onAttach(view: ListStandingsView) {
        mView = view
    }

    override fun onDetach() {
        mView = null
    }

    fun setStandings(uid: String) {

        mView?.showLoading()

        val query = FirebaseDatabase.getInstance().reference.child("standings").child(uid)
        query.keepSynced(true)
        val options = FirebaseRecyclerOptions.Builder<StandingsModel>()
                .setQuery(query, StandingsModel::class.java).build()

        mView?.setListStandings(options)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                mView?.errorCancelled()
                mView?.hideLoading()
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(!p0.exists()) mView?.dataEmpty() else mView?.dataNotEmpty()
                mView?.hideLoading()
            }

        })
    }
}