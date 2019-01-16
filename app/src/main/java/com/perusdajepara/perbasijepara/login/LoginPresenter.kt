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

        mAuth.signInWithEmailAndPassword(email, pass).addOnSuccessListener {
            if (mAuth.currentUser?.isEmailVerified as Boolean) {
                mView?.hideLoading()
                mView?.suksesLogin()
            } else {
                mView?.hideLoading()
                mView?.emailNotVerified()
            }
        }.addOnFailureListener {
            mView?.hideLoading()
            mView?.userNotFound()
        }
    }

    fun resetPass(email: String) {

        mView?.showLoading()

        mAuth.sendPasswordResetEmail(email).addOnSuccessListener {
            mView?.hideLoading()
            mView?.suksesResetPass()
        }.addOnFailureListener {
            mView?.hideLoading()
            mView?.gagalResetPass()
        }
    }
}