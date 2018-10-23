package com.perusdajepara.perbasijepara.informasi.event.overview

import com.google.firebase.database.*
import com.perusdajepara.perbasijepara.basecontract.BasePresenter
import com.perusdajepara.perbasijepara.informasi.event.EventModel

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

        listener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                mView?.setDataError()
            }

            override fun onDataChange(p0: DataSnapshot) {

                val eventModel = EventModel()
                eventModel.lokasi = p0.child("lokasi").value.toString()
                eventModel.alamat = p0.child("alamat").value.toString()
                eventModel.tanggal = p0.child("tanggal").value.toString()
                eventModel.teamTerdaftar = p0.child("teamTerdaftar").value.toString().toLong()
                eventModel.deskripsi = p0.child("deskripsi").value.toString()

                mView?.setData(eventModel)
            }

        }

        mDatabase.addValueEventListener(listener)
    }

    fun removeListener() {
        mDatabase.removeEventListener(listener)
    }
}