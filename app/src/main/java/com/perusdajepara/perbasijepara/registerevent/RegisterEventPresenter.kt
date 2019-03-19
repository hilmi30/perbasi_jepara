package com.perusdajepara.perbasijepara.registerevent

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.perusdajepara.perbasijepara.basecontract.BasePresenter
import java.util.*

class RegisterEventPresenter: BasePresenter<RegisterEventView> {

    var mView: RegisterEventView? = null
    private lateinit var mDatabase: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

    override fun onAttach(view: RegisterEventView) {
        mView = view
    }

    override fun onDetach() {
        mView = null
    }

    fun initFirebase() {
        mDatabase = FirebaseDatabase.getInstance().reference
        mAuth = FirebaseAuth.getInstance()
    }

    fun kirimDaftarEvent(namaTeam: String, emailTeam: String, noTelp: String, uidEvent: String) {
        mView?.showLoading()

        val teamModel = TeamModel()
        teamModel.email = emailTeam
        teamModel.nama = namaTeam
        teamModel.noTelp = noTelp
        teamModel.kategoriEvent = uidEvent
        teamModel.uidUser = mAuth.currentUser?.uid.toString()

        mDatabase.child("teamTerdaftar/${teamModel.uidUser}").setValue(teamModel).addOnSuccessListener {
            mView?.daftarEventBerhasil()
            mView?.hideLoading()
        }.addOnFailureListener {
            mView?.gagalDaftarEvent()
        }
    }
}