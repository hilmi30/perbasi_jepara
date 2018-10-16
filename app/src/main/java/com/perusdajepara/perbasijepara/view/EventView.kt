package com.perusdajepara.perbasijepara.view

import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.perusdajepara.perbasijepara.basecontract.BaseView
import com.perusdajepara.perbasijepara.fragment.EventFragment
import com.perusdajepara.perbasijepara.model.EventModel

interface EventView: BaseView {
    fun setHolder(holder: EventFragment.EventHolder, position: Int, model: EventModel)
    fun showList(adapter: FirebaseRecyclerAdapter<EventModel, EventFragment.EventHolder>)
}