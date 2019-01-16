package com.perusdajepara.perbasijepara.profil


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseUser

import com.perusdajepara.perbasijepara.R
import com.perusdajepara.perbasijepara.login.LoginActivity
import com.perusdajepara.perbasijepara.model.UserModel
import com.perusdajepara.perbasijepara.signup.SignupActivity
import com.perusdajepara.perbasijepara.utils.gone
import com.perusdajepara.perbasijepara.utils.visible
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast


class ProfileFragment : Fragment(), ProfileView {

    private lateinit var presenter: ProfilePresenter
    private lateinit var displayName: String
    private lateinit var photoUrl: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        onAttachView()
    }

    override fun onAttachView() {
        presenter = ProfilePresenter()
        presenter.onAttach(this)
        presenter.initFirebase()
        setButton()
    }

    private fun setButton() {
        logout_btn.setOnClickListener {

            alert {
                title = getString(R.string.logout)
                message = getString(R.string.yakin_ingin_logout)
                positiveButton(getString(R.string.ya)) {
                    presenter.signOut()
                    startActivity<LoginActivity>()
                }
                negativeButton(getString(R.string.tidak)) { alert ->
                    alert.dismiss()
                }
            }.show()
        }

        login_profile_btn.setOnClickListener {
            startActivity<LoginActivity>()
        }
    }

    override fun onDetachView() {
        presenter.onDetach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onDetachView()
    }

    override fun onStart() {
        super.onStart()
        presenter.checkUser()
    }

    override fun adaUser() {
        logout_btn.visible()
        login_profile_btn.gone()
        tv_login_atau_register.gone()
        card_profile.visible()

        presenter.setProfile()
    }

    override fun tidakAdaUser() {
        logout_btn.gone()
        login_profile_btn.visible()
        tv_login_atau_register.visible()
        card_profile.gone()
    }

    override fun errorCancelled() {
        toast(getString(R.string.terjadi_kesalahan))
        btn_edit_profile.isEnabled = false
    }

    override fun showUserProfile(userModel: UserModel?) {

        btn_edit_profile.isEnabled = true

        val gender: String = if (userModel?.jenisKelamin == 1) "Laki-laki" else "Perempuan"
        tv_gender.text = gender
        tv_alamat.text = userModel?.alamat?.toUpperCase()
        tv_tanggal_lahir.text = userModel?.tanggalLahir

        btn_edit_profile.setOnClickListener {
            startActivity<SignupActivity>(
                    getString(R.string.update_profile) to "1",
                    getString(R.string.nama) to displayName,
                    getString(R.string.alamat) to userModel?.alamat,
                    getString(R.string.tanggalLahir) to userModel?.tanggalLahir,
                    getString(R.string.gender) to userModel?.jenisKelamin.toString(),
                    getString(R.string.imgUser) to photoUrl
            )
        }
    }

    override fun setProfileAuth(user: FirebaseUser?) {

        displayName = user?.displayName.toString()
        photoUrl = user?.photoUrl.toString()

        tv_nama_user.text = displayName
        tv_email.text = user?.email
        Picasso.get().load(photoUrl).placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background).into(img_profile)
    }

}
