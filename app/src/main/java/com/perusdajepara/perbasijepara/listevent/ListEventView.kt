package com.perusdajepara.perbasijepara.listevent

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.perusdajepara.perbasijepara.basecontract.BaseView
import com.perusdajepara.perbasijepara.model.EventModel

interface ListEventView: BaseView {
    fun showList(options: FirebaseRecyclerOptions<EventModel>)
    fun showLoading()
    fun errorCancelled()
    fun dataNotEmpty()
    fun dataEmpty()
    fun hideLoading()
}