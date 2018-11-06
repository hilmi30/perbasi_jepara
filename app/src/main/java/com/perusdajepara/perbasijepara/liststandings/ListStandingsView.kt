package com.perusdajepara.perbasijepara.liststandings

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.perusdajepara.perbasijepara.basecontract.BaseView
import com.perusdajepara.perbasijepara.model.StandingsModel

interface ListStandingsView: BaseView {
    fun setListStandings(options: FirebaseRecyclerOptions<StandingsModel>)
    fun showLoading()
    fun errorCancelled()
    fun hideLoading()
    fun dataEmpty()
    fun dataNotEmpty()
}