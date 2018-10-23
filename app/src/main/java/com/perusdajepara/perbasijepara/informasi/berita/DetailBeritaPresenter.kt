package com.perusdajepara.perbasijepara.informasi.berita

import com.google.firebase.database.*
import com.perusdajepara.perbasijepara.basecontract.BasePresenter

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
        mDatabase = FirebaseDatabase.getInstance().reference
                .child("berita").child(uid)

        listener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                mView?.getDataError()
            }

            override fun onDataChange(p0: DataSnapshot) {

                val beritaModel = BeritaModel()
                beritaModel.thumbnail = p0.child("thumbnail").value.toString()
                beritaModel.judul = p0.child("judul").value.toString()
                beritaModel.updatedAt = p0.child("updatedAt").value.toString()
                beritaModel.deskripsi = p0.child("deskripsi").value.toString()
                beritaModel.createdAt = p0.child("createdAt").value.toString()

                mView?.showDataBerita(beritaModel)
            }

        }

        mDatabase.addValueEventListener(listener)
    }

    fun removeListener() {
        mDatabase.removeEventListener(listener)
    }
}