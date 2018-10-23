package com.perusdajepara.perbasijepara.informasi.event.detailevent

import com.google.firebase.database.*
import com.perusdajepara.perbasijepara.basecontract.BasePresenter
import com.perusdajepara.perbasijepara.informasi.event.EventModel

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

                val eventModel = EventModel()
                eventModel.uid = uid
                eventModel.thumbnail = p0.child("thumbnail").value.toString()
                eventModel.judul = p0.child("judul").value.toString()

                mView?.setDataEvent(eventModel)
            }

        }

        mDatabase.addListenerForSingleValueEvent(listener)
    }

    fun removeListener() {
        mDatabase.removeEventListener(listener)
    }

}