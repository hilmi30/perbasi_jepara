package com.perusdajepara.perbasijepara.login

import com.google.firebase.auth.FirebaseAuth
import com.perusdajepara.perbasijepara.basecontract.BasePresenter

class LoginPresenter: BasePresenter<LoginView> {

    private var mView: LoginView? = null
    private lateinit var mAuth: FirebaseAuth

    override fun onAttach(view: LoginView) {
        mView = view
    }

    override fun onDetach() {
        mView = null
    }

    fun initFirebase() {
        mAuth = FirebaseAuth.getInstance()
    }

    fun login(email: String, pass: String) {

        mView?.showLoading()

        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
            if (it.isSuccessful) {
                mView?.hideLoading()
                mView?.suksesLogin()
            } else {
                mView?.userNotFound()
            }
        }.addOnFailureListener {
            mView?.hideLoading()
            mView?.gagalLogin()
        }
    }
}