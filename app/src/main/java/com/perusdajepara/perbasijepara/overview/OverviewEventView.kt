package com.perusdajepara.perbasijepara.overview

import com.perusdajepara.perbasijepara.basecontract.BaseView
import com.perusdajepara.perbasijepara.model.EventModel

interface OverviewEventView: BaseView {
    fun setDataError()
    fun setData(eventModel: EventModel?)
}