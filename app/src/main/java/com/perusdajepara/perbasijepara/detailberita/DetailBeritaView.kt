package com.perusdajepara.perbasijepara.detailberita

import com.perusdajepara.perbasijepara.basecontract.BaseView
import com.perusdajepara.perbasijepara.model.BeritaModel

interface DetailBeritaView: BaseView {
    fun getDataError()
    fun showDataBerita(beritaModel: BeritaModel?)
}