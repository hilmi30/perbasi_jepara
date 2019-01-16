package com.perusdajepara.perbasijepara.signup

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
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
                 tanggalLahir: String?, alamat: String, imgUri: Uri) {

        mView?.showLoading()

        mAuth.createUserWithEmailAndPassword(email, pass).addOnSuccessListener {
            mView?.hideLoading()

            // kirim email verifikasi
            it.user.sendEmailVerification()

            it.user.updateProfile(UserProfileChangeRequest.Builder()
                    .setDisplayName(namaLengkap)
                    .setPhotoUri(imgUri)
                    .build())

            saveUserToDatabase(jenisKelamin, tanggalLahir, it.user.uid, alamat)
        }.addOnFailureListener {
            mView?.hideLoading()
            mView?.failure()
        }
    }

    private fun saveUserToDatabase(jenisKelamin: Int?, tanggalLahir: String?, uid: String, alamat: String) {

        val userModel = UserModel()
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

    fun updateProfile(namaLengkap: String, jenisKelamin: Int?, tanggalLahir: String?, alamat: String, imgUri: Uri, pass: String, oldPass: String) {

        mView?.showLoading()

        val userUID = mAuth.currentUser?.uid
        val user = mAuth.currentUser

        if (user == null) {
            mView?.gotToMain()
            return
        }

        val userModel = UserModel()
        userModel.jenisKelamin = jenisKelamin
        userModel.tanggalLahir = tanggalLahir
        userModel.uidUser = userUID
        userModel.alamat = alamat

        mDatabase.child("user/$userUID").setValue(userModel).addOnSuccessListener {
            // update data profile
            user.updateProfile(UserProfileChangeRequest.Builder()
                    .setDisplayName(namaLengkap)
                    .setPhotoUri(imgUri)
                    .build()).addOnSuccessListener {

                if (pass.isNotEmpty()) {

                    val credential = EmailAuthProvider.getCredential(user.email.toString(), oldPass)

                    user.reauthenticate(credential).addOnSuccessListener {
                        user.updatePassword(pass).addOnSuccessListener {
                            mView?.hideLoading()
                            mView?.successUpdate()
                        }.addOnFailureListener { error ->
                            mView?.hideLoading()
                            Log.e("error", error.toString())
                            mView?.failure()
                        }
                    }.addOnFailureListener {
                        mView?.hideLoading()
                        mView?.failure()
                    }
                } else {
                    mView?.hideLoading()
                    mView?.successUpdate()
                }
            }.addOnFailureListener {
                mView?.hideLoading()
                mView?.failure()
            }
        }.addOnFailureListener {
            mView?.hideLoading()
            mView?.failure()
        }
    }

    fun setDataProfile() {
        val user = mAuth.currentUser
        mView?.setDataProfile(user)
    }
}
