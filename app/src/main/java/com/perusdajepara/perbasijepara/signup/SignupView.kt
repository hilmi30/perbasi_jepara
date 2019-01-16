package com.perusdajepara.perbasijepara.signup

import com.google.firebase.auth.FirebaseUser
import com.perusdajepara.perbasijepara.basecontract.BaseView

interface SignupView: BaseView {
    fun errorNama()
    fun errorEmail()
    fun errorPass()
    fun errorPassTidakSama()
    fun errorPassKurang()
    fun failure()
    fun successRegister()
    fun showLoading()
    fun hideLoading()
    fun successUpdate()
    fun gotToMain()
    fun setDataProfile(user: FirebaseUser?)
}