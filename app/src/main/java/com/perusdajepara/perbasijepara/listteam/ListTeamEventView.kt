package com.perusdajepara.perbasijepara.listteam

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.perusdajepara.perbasijepara.basecontract.BaseView
import com.perusdajepara.perbasijepara.model.TeamEventModel

interface ListTeamEventView: BaseView {
    fun showList(options: FirebaseRecyclerOptions<TeamEventModel>)
    fun hideLoading()
    fun errorCancelled()
    fun showLoading()
    fun dataEmpty()
    fun dataNotEmpty()
}