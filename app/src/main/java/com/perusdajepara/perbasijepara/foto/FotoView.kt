package com.perusdajepara.perbasijepara.foto

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.perusdajepara.perbasijepara.basecontract.BaseView

interface FotoView: BaseView {
    fun setListFoto(options: FirebaseRecyclerOptions<FotoModel>)
}