package com.perusdajepara.perbasijepara.login

import com.perusdajepara.perbasijepara.basecontract.BaseView

interface LoginView: BaseView {
    fun gagalLogin()
    fun suksesLogin()
    fun showLoading()
    fun hideLoading()
    fun userNotFound()
    fun emailNotVerified()
    fun suksesResetPass()
    fun gagalResetPass()
}