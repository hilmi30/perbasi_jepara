package com.perusdajepara.perbasijepara.basecontract

interface BasePresenter<T: BaseView> {
    fun onAttach(view: T)
    fun onDetach()
}