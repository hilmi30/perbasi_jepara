package com.perusdajepara.perbasijepara.registerevent

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.perusdajepara.perbasijepara.basecontract.BasePresenter
import java.util.*

class RegisterEventPresenter: BasePresenter<RegisterEventView> {

    var mView: RegisterEventView? = null
    private lateinit var mDatabase: DatabaseReference

    override fun onAttach(view: RegisterEventView) {
        mView = view
    }

    override fun onDetach() {
        mView = null
    }

    fun initFirebase() {
        mDatabase = FirebaseDatabase.getInstance().reference
    }

    fun kirimDaftarEvent(namaTeam: String, emailTeam: String, noTelp: String, uidEvent: String) {
        mView?.showLoading()

        val teamModel = TeamModel()
        teamModel.email = emailTeam
        teamModel.nama = namaTeam
        teamModel.noTelp = noTelp
        teamModel.kategoriEvent = uidEvent




        mDatabase.child("teamTerdaftar").push().setValue("")
    }
}