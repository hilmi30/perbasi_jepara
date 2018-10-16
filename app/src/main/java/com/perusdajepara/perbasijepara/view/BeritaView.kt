package com.perusdajepara.perbasijepara.view

import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.perusdajepara.perbasijepara.basecontract.BaseView
import com.perusdajepara.perbasijepara.fragment.BeritaFragment
import com.perusdajepara.perbasijepara.model.BeritaModel

interface BeritaView: BaseView {
    fun setHolder(holder: BeritaFragment.BeritaHolder, position: Int, model: BeritaModel)
    fun showList(adapter: FirebaseRecyclerAdapter<BeritaModel, BeritaFragment.BeritaHolder>)
}