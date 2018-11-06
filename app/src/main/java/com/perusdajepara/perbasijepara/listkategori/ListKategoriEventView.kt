package com.perusdajepara.perbasijepara.listkategori

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.perusdajepara.perbasijepara.basecontract.BaseView
import com.perusdajepara.perbasijepara.model.KategoriEventModel

interface ListKategoriEventView: BaseView {
    fun showEventKategori(options: FirebaseRecyclerOptions<KategoriEventModel>)
    fun showLoading()
    fun errorCancelled()
    fun hideLoading()
    fun dataEmpty()
    fun dataNotEmpty()
}