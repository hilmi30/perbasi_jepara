package com.perusdajepara.perbasijepara.informasi.event

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.perusdajepara.perbasijepara.basecontract.BaseView

interface EventView: BaseView {
    fun showList(options: FirebaseRecyclerOptions<EventModel>)
    fun showLoading()
}