package com.perusdajepara.perbasijepara.signup

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.perusdajepara.perbasijepara.basecontract.BasePresenter
import com.perusdajepara.perbasijepara.model.UserModel

class SignupPresenter: BasePresenter<SignupView> {

    private var mView: SignupView? = null
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference

    override fun onAttach(view: SignupView) {
        mView = view
    }

    override fun onDetach() {
        mView = null
    }

    fun initFirebase() {
        mDatabase = FirebaseDatabase.getInstance().reference
        mAuth = FirebaseAuth.getInstance()
    }

    fun register(namaLengkap: String, email: String, pass: String, jenisKelamin: Int?,
                 tanggalLahir: String?, alamat: String) {

        mView?.showLoading()

        mAuth.createUserWithEmailAndPassword(email, pass).addOnSuccessListener {
            saveUserToDatabase(namaLengkap, email, jenisKelamin, tanggalLahir, it.user.uid, alamat)
        }.addOnFailureListener {
            mView?.hideLoading()
            mView?.failure()
        }
    }

    private fun saveUserToDatabase(namaLengkap: String, email: String, jenisKelamin: Int?,
                                   tanggalLahir: String?, uid: String, alamat: String) {

        val userModel = UserModel()
        userModel.nama = namaLengkap
        userModel.email = email
        userModel.jenisKelamin = jenisKelamin
        userModel.tanggalLahir = tanggalLahir
        userModel.uidUser = uid
        userModel.alamat = alamat

        mDatabase.child("user/$uid").setValue(userModel).addOnSuccessListener {
            mView?.hideLoading()
            mView?.successRegister()
        }.addOnFailureListener {
            mView?.hideLoading()
            mView?.failure()
        }
    }

    fun updateProfile(namaLengkap: String, jenisKelamin: Int?, tanggalLahir: String?, alamat: String) {

        mView?.showLoading()

        val userUID = mAuth.currentUser?.uid
        val email = mAuth.currentUser?.email

        val userModel = UserModel()
        userModel.nama = namaLengkap
        userModel.jenisKelamin = jenisKelamin
        userModel.tanggalLahir = tanggalLahir
        userModel.uidUser = userUID
        userModel.alamat = alamat
        userModel.email = email

        mDatabase.child("user/$userUID").setValue(userModel).addOnSuccessListener {
            mView?.hideLoading()
            mView?.successUpdate()
        }.addOnFailureListener {
            mView?.hideLoading()
            mView?.failure()
        }
    }
}