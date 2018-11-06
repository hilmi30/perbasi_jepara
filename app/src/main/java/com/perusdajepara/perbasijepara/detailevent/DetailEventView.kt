package com.perusdajepara.perbasijepara.detailevent

import com.perusdajepara.perbasijepara.basecontract.BaseView
import com.perusdajepara.perbasijepara.model.EventModel

interface DetailEventView: BaseView {
    fun getDataError()
    fun setDataEvent(eventModel: EventModel?, eventKey: String)
}