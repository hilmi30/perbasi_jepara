package com.perusdajepara.perbasijepara.view

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.perusdajepara.perbasijepara.basecontract.BaseView
import com.perusdajepara.perbasijepara.event.EventModel

interface EventView: BaseView {
    fun showList(options: FirebaseRecyclerOptions<EventModel>)
    fun showLoading()
}