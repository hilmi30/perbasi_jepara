package com.perusdajepara.perbasijepara.listberita

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.perusdajepara.perbasijepara.basecontract.BaseView
import com.perusdajepara.perbasijepara.model.BeritaModel

interface ListBeritaView: BaseView {
    fun showList(options: FirebaseRecyclerOptions<BeritaModel>)
    fun hideLoading()
    fun dataEmpty()
    fun dataNotEmpty()
    fun showLoading()
    fun errorCancelled()
}