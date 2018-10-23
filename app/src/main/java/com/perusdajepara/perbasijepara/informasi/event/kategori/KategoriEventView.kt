package com.perusdajepara.perbasijepara.informasi.event.kategori

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.perusdajepara.perbasijepara.basecontract.BaseView

interface KategoriEventView: BaseView {
    fun showEventKategori(options: FirebaseRecyclerOptions<KategoriEventModel>)
}