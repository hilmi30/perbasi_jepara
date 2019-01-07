package com.perusdajepara.perbasijepara.listjadwal

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.perusdajepara.perbasijepara.basecontract.BasePresenter
import com.perusdajepara.perbasijepara.model.JadwalModel

class ListJadwalPresenter: BasePresenter<ListJadwalView> {

    var mView: ListJadwalView? = null

    override fun onAttach(view: ListJadwalView) {
        mView = view
    }

    override fun onDetach() {
        mView = null
    }

    fun setListJadwal(kategori: String) {

        mView?.showLoading()

        val query = FirebaseDatabase.getInstance().reference.child("jadwal")
                .orderByChild("kategori").equalTo(kategori)
        query.keepSynced(true)
        val options = FirebaseRecyclerOptions.Builder<JadwalModel>()
                .setQuery(query, JadwalModel::class.java).build()

        mView?.showList(options)

        query.addListenerForSingleValueEvent(object : ValueEventListener{
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