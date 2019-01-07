package com.perusdajepara.perbasijepara.video

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.perusdajepara.perbasijepara.basecontract.BaseView

interface VideoView: BaseView {
    fun setToAdapter(options: FirebaseRecyclerOptions<VideoModel>)
    fun errorCancelled()
    fun dataEmpty()
    fun dataNotEmpty()
    fun hideLoading()
    fun showLoading()
}