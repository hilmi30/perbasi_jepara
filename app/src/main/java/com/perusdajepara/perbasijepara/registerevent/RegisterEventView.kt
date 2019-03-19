package com.perusdajepara.perbasijepara.registerevent

import com.perusdajepara.perbasijepara.basecontract.BaseView

interface RegisterEventView: BaseView {
    fun showLoading()
    fun hideLoading()
    fun daftarEventBerhasil()
    fun gagalDaftarEvent()
}