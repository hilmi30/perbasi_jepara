package com.perusdajepara.perbasijepara.informasi.berita

import com.perusdajepara.perbasijepara.basecontract.BaseView

interface DetailBeritaView: BaseView {
    fun getDataError()
    fun showDataBerita(beritaModel: BeritaModel)
}