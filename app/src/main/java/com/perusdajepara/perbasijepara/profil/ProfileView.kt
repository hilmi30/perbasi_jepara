package com.perusdajepara.perbasijepara.profil

import com.google.firebase.auth.FirebaseUser
import com.perusdajepara.perbasijepara.basecontract.BaseView
import com.perusdajepara.perbasijepara.model.UserModel

interface ProfileView: BaseView {
    fun adaUser()
    fun tidakAdaUser()
    fun errorCancelled()
    fun showUserProfile(userModel: UserModel?)
    fun setProfileAuth(user: FirebaseUser?)
}