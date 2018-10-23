package com.perusdajepara.perbasijepara.informasi.event.overview

import com.perusdajepara.perbasijepara.basecontract.BaseView
import com.perusdajepara.perbasijepara.informasi.event.EventModel

interface OverviewEventView: BaseView {
    fun setDataError()
    fun setData(eventModel: EventModel)
}