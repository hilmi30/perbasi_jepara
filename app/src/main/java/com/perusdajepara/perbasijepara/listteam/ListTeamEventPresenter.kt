package com.perusdajepara.perbasijepara.listteam

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.perusdajepara.perbasijepara.basecontract.BasePresenter
import com.perusdajepara.perbasijepara.model.TeamEventModel

class ListTeamEventPresenter: BasePresenter<ListTeamEventView> {

    var mView: ListTeamEventView? = null

    override fun onAttach(view: ListTeamEventView) {
        mView = view
    }

    override fun onDetach() {
        mView = null
    }

    fun setTeamList(uid: String) {

        mView?.showLoading()

        val query = FirebaseDatabase.getInstance().reference
                .child("teamTerdaftar").orderByChild("kategori").equalTo(uid)
        query.keepSynced(true)

        val options = FirebaseRecyclerOptions
                .Builder<TeamEventModel>()
                .setQuery(query, TeamEventModel::class.java).build()

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