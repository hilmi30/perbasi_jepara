package com.perusdajepara.perbasijepara.signup

import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.perusdajepara.perbasijepara.basecontract.BasePresenter
import com.perusdajepara.perbasijepara.model.UserModel

class SignupPresenter: BasePresenter<SignupView> {

    private var mView: SignupView? = null
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference
    private lateinit var mStorage: StorageReference

    override fun onAttach(view: SignupView) {
        mView = view
    }

    override fun onDetach() {
        mView = null
    }

    fun initFirebase() {
        mDatabase = FirebaseDatabase.getInstance().reference
        mAuth = FirebaseAuth.getInstance()
        mStorage = FirebaseStorage.getInstance().reference
    }

    fun register(namaLengkap: String, email: String, pass: String, jenisKelamin: Int?,
                 tanggalLahir: String?, alamat: String, imageByteArray: ByteArray, thumbnail: Bitmap?) {

        mView?.showLoading()

        mAuth.createUserWithEmailAndPassword(email, pass).addOnSuccessListener {
            mView?.hideLoading()
            saveUserToDatabase(namaLengkap, email, jenisKelamin, tanggalLahir, it.user.uid, alamat,
                    imageByteArray, thumbnail)
        }.addOnFailureListener {
            mView?.hideLoading()
            mView?.failure()
        }
    }

    private fun saveUserToDatabase(namaLengkap: String, email: String, jenisKelamin: Int?,
                                   tanggalLahir: String?, uid: String, alamat: String, imageByteArray: ByteArray,
                                   thumbnail: Bitmap?) {

        val userModel = UserModel()
        userModel.nama = namaLengkap
        userModel.email = email
        userModel.jenisKelamin = jenisKelamin
        userModel.tanggalLahir = tanggalLahir
        userModel.uidUser = uid
        userModel.alamat = alamat

        mDatabase.child("user/$uid").setValue(userModel).addOnSuccessListener {
            if (thumbnail != null) {
                uploadGambar(imageByteArray, uid, "resgister")
            }  else {
                mView?.hideLoading()
                mView?.successRegister()
            }
        }.addOnFailureListener {
            mView?.hideLoading()
            mView?.failure()
        }
    }

    fun updateProfile(namaLengkap: String, jenisKelamin: Int?, tanggalLahir: String?, alamat: String,
                      imageByteArray: ByteArray, thumbnail: Bitmap?, imgUser: String) {

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
        userModel.imgUser = imgUser

        mDatabase.child("user/$userUID").setValue(userModel).addOnSuccessListener {
            if (thumbnail != null) {
                uploadGambar(imageByteArray, userUID as String, "update")
            }  else {
                mView?.hideLoading()
                mView?.successUpdate()
            }
        }.addOnFailureListener {
            mView?.hideLoading()
            mView?.failure()
        }
    }

    private fun uploadGambar(imageByteArray: ByteArray, uid: String, s: String) {

        mView?.showLoading()

        val imgRoot = mStorage.child("foto/$uid")
        val uploadTask = imgRoot.putBytes(imageByteArray)

        // upload gambar ke firebase storage
        uploadTask
                .addOnSuccessListener {
                    imgRoot.downloadUrl.addOnSuccessListener { uri ->
                        mDatabase.child("user/$uid/imgUser").setValue(uri.toString())
                                .addOnFailureListener {
                                    mView?.hideLoading()
                                    mView?.failure()
                                }.addOnSuccessListener {
                                    when (s) {
                                        "update" -> mView?.successUpdate()
                                        "register" -> mView?.successRegister()
                                    }
                                    mView?.hideLoading()
                                }
                    }.addOnFailureListener {
                        mView?.hideLoading()
                        mView?.failure()
                    }

                }.addOnFailureListener {
                    mView?.hideLoading()
                }
    }
}
