package com.perusdajepara.perbasijepara.informasi.berita

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.perusdajepara.perbasijepara.basecontract.BaseView

interface BeritaView: BaseView {
    fun showList(options: FirebaseRecyclerOptions<BeritaModel>)
}