package com.perusdajepara.perbasijepara.view

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.perusdajepara.perbasijepara.basecontract.BaseView
import com.perusdajepara.perbasijepara.model.KategoriEventModel

interface KategoriEventView: BaseView {
    fun showEventKategori(options: FirebaseRecyclerOptions<KategoriEventModel>)
}