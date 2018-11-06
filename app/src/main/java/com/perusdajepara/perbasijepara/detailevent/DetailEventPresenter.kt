package com.perusdajepara.perbasijepara.detailevent

import com.google.firebase.database.*
import com.perusdajepara.perbasijepara.basecontract.BasePresenter
import com.perusdajepara.perbasijepara.model.EventModel

class DetailEventPresenter: BasePresenter<DetailEventView> {

    var mView: DetailEventView? = null
    private lateinit var mDatabase: DatabaseReference
    private lateinit var listener: ValueEventListener

    override fun onAttach(view: DetailEventView) {
        mView = view
    }

    override fun onDetach() {
        mView = null
    }

    fun showDetailEvent(uid: String) {
        mDatabase = FirebaseDatabase.getInstance().reference
                .child("event").child(uid)

        listener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                mView?.getDataError()
            }

            override fun onDataChange(p0: DataSnapshot) {
                val eventModel = p0.getValue(EventModel::class.java)
                val eventKey = p0.key.toString()
                mView?.setDataEvent(eventModel, eventKey)
            }

        }

        mDatabase.addListenerForSingleValueEvent(listener)
    }

    fun removeListener() {
        mDatabase.removeEventListener(listener)
    }

}