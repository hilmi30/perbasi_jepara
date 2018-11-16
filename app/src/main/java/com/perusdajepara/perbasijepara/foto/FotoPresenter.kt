package com.perusdajepara.perbasijepara.foto

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.perusdajepara.perbasijepara.basecontract.BasePresenter

class FotoPresenter: BasePresenter<FotoView> {

    var mView: FotoView? = null

    override fun onAttach(view: FotoView) {
        mView = view
    }

    override fun onDetach() {
        mView = null
    }

    fun setFoto() {
        val query = FirebaseDatabase.getInstance().reference.child("foto")
        val options = FirebaseRecyclerOptions.Builder<FotoModel>()
                .setQuery(query, FotoModel::class.java).build()

        mView?.setListFoto(options)
    }


}