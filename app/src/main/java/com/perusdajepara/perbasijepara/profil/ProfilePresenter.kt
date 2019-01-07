package com.perusdajepara.perbasijepara.profil

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.perusdajepara.perbasijepara.basecontract.BasePresenter
import com.perusdajepara.perbasijepara.model.UserModel

class ProfilePresenter: BasePresenter<ProfileView> {

    private var mView: ProfileView? = null
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference

    override fun onAttach(view: ProfileView) {
        mView = view
    }

    override fun onDetach() {
        mView = null
    }

    fun initFirebase() {
        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference
    }

    fun setProfile() {
        val authUid = mAuth.currentUser?.uid
        mDatabase.child("user/$authUid").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                mView?.errorCancelled()
            }

            override fun onDataChange(p0: DataSnapshot) {
                val userModel = p0.getValue(UserModel::class.java)
                mView?.showUserProfile(userModel)
            }
        })
    }

    fun checkUser() {
        if (mAuth.currentUser != null) {
            mView?.adaUser()
        } else {
            mView?.tidakAdaUser()
        }
    }

    fun signOut() {
        mAuth.signOut()
    }
}