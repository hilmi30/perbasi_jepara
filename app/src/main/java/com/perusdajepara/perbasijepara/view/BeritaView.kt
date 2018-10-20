package com.perusdajepara.perbasijepara.view

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.perusdajepara.perbasijepara.basecontract.BaseView
import com.perusdajepara.perbasijepara.berita.BeritaModel

interface BeritaView: BaseView {
    fun showList(options: FirebaseRecyclerOptions<BeritaModel>)
}