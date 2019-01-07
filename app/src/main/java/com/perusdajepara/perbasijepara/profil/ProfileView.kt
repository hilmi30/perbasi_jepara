package com.perusdajepara.perbasijepara.profil

import com.perusdajepara.perbasijepara.basecontract.BaseView
import com.perusdajepara.perbasijepara.model.UserModel

interface ProfileView: BaseView {
    fun adaUser()
    fun tidakAdaUser()
    fun errorCancelled()
    fun showUserProfile(userModel: UserModel?)
}