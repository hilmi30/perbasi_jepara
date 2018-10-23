package com.perusdajepara.perbasijepara.informasi.event.detailevent

import com.perusdajepara.perbasijepara.basecontract.BaseView
import com.perusdajepara.perbasijepara.informasi.event.EventModel

interface DetailEventView: BaseView {
    fun getDataError()
    fun setDataEvent(eventModel: EventModel)
}