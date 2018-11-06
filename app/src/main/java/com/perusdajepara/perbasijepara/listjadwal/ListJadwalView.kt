package com.perusdajepara.perbasijepara.listjadwal

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.perusdajepara.perbasijepara.basecontract.BaseView
import com.perusdajepara.perbasijepara.model.JadwalModel

interface ListJadwalView: BaseView {
    fun showList(options: FirebaseRecyclerOptions<JadwalModel>)
    fun hideLoading()
    fun showLoading()
    fun errorCancelled()
    fun dataEmpty()
    fun dataNotEmpty()
}