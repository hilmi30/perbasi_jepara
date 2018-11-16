package com.perusdajepara.perbasijepara.overview

import com.google.firebase.database.*
import com.perusdajepara.perbasijepara.basecontract.BasePresenter
import com.perusdajepara.perbasijepara.model.EventModel

class OverviewEventPresenter: BasePresenter<OverviewEventView> {

    var mView: OverviewEventView? = null
    private lateinit var listener: ValueEventListener
    private lateinit var mDatabase: DatabaseReference

    override fun onAttach(view: OverviewEventView) {
        mView = view
    }

    override fun onDetach() {
        mView = null
    }

    fun setOverviewItem(eventUid: String) {
        mDatabase = FirebaseDatabase.getInstance().reference
                .child("event").child(eventUid)
        mDatabase.keepSynced(true)

        listener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                mView?.setDataError()
            }

            override fun onDataChange(p0: DataSnapshot) {
                val eventModel = p0.getValue(EventModel::class.java)
                mView?.setData(eventModel)
            }

        }

        mDatabase.addValueEventListener(listener)
    }

    fun removeListener() {
        mDatabase.removeEventListener(listener)
    }
}