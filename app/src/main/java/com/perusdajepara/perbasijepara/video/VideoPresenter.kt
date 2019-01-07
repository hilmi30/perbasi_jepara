package com.perusdajepara.perbasijepara.video

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.perusdajepara.perbasijepara.basecontract.BasePresenter

class VideoPresenter: BasePresenter<VideoView> {

    var mView: VideoView? = null

    override fun onAttach(view: VideoView) {
        mView = view
    }

    override fun onDetach() {
        mView = null
    }

    fun setListVideo() {
        mView?.showLoading()
        val query = FirebaseDatabase.getInstance().reference.child("video")
        query.keepSynced(true)
        val options = FirebaseRecyclerOptions.Builder<VideoModel>()
                .setQuery(query, VideoModel::class.java).build()

        mView?.setToAdapter(options)

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