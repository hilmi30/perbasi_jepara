package com.perusdajepara.perbasijepara.detailberita

import com.google.firebase.database.*
import com.perusdajepara.perbasijepara.basecontract.BasePresenter
import com.perusdajepara.perbasijepara.model.BeritaModel

class DetailBeritaPresenter: BasePresenter<DetailBeritaView> {

    var mView: DetailBeritaView? = null
    private lateinit var mDatabase: DatabaseReference
    private lateinit var listener: ValueEventListener

    override fun onAttach(view: DetailBeritaView) {
        mView = view
    }

    override fun onDetach() {
        mView = null
    }

    fun setItemBerita(uid: String) {
        mDatabase = FirebaseDatabase.getInstance().reference.child("berita").child(uid)

        listener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                mView?.getDataError()
            }

            override fun onDataChange(p0: DataSnapshot) {
                val beritaModel = p0.getValue(BeritaModel::class.java)
                mView?.showDataBerita(beritaModel)
            }

        }

        mDatabase.addValueEventListener(listener)
    }

    fun removeListener() {
        mDatabase.removeEventListener(listener)
    }
}