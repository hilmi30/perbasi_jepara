package com.perusdajepara.perbasijepara.login

import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.perusdajepara.perbasijepara.R
import com.perusdajepara.perbasijepara.activity.MainActivity
import com.perusdajepara.perbasijepara.signup.SignupActivity
import com.perusdajepara.perbasijepara.utils.gone
import com.perusdajepara.perbasijepara.utils.visible
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class LoginActivity : AppCompatActivity(), LoginView {

    private lateinit var presenter: LoginPresenter
    private lateinit var alertDialog: DialogInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        onAttachView()
    }

    override fun onAttachView() {
        presenter = LoginPresenter()
        presenter.onAttach(this)
        presenter.initFirebase()

        signupBtn()
        loginBtn()
        lupaPassBtn()
    }

    private fun lupaPassBtn() {
        lupa_pass_btn.setOnClickListener {
           alertDialog = alert {
                customView {
                    verticalLayout {

                        padding = dip(16)

                        val emailEdt = editText {
                            hint = "Masukkan email"
                        }

                        val kirimBtn = button {
                            text = "Kirim"
                        }

                        kirimBtn.setOnClickListener {
                            val email = emailEdt.text.toString()
                            if (email.isEmpty()) {
                                emailEdt.error = "Email tidak boleh kosong"
                            } else {
                                presenter.resetPass(email)
                            }
                        }
                    }
                }
            }.show()
        }
    }

    private fun loginBtn() {
        login_btn.setOnClickListener {
            val email = email_login_edt.text.toString()
            val pass = password_login_edt.text.toString()

            if (email.isEmpty()) email_login_edt.error = "email tidak boleh kosong"
            if (pass.isEmpty()) password_login_edt.error = "password tidak boleh kosong"

            if (email.isNotEmpty() && pass.isNotEmpty()) presenter.login(email, pass)
        }
    }

    private fun signupBtn() {
        signup_btn.setOnClickListener {
            startActivity<SignupActivity>(
                    getString(R.string.update_profile) to "0"
            )
        }
    }

    override fun onDetachView() {
        presenter.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
        onDetachView()
    }

    override fun gagalLogin() {
        toast(getString(R.string.terjadi_kesalahan))
    }

    override fun suksesLogin() {
        startActivity<MainActivity>()
        finish()
    }

    override fun showLoading() {
        login_progress.visible()
    }

    override fun hideLoading() {
        login_progress.gone()
    }

    override fun userNotFound() {
        toast("email atau password invalid")
    }

    override fun emailNotVerified() {
        toast("email anda belum terverifikasi")
    }

    override fun suksesResetPass() {
        toast("Email reset pass berhasil dikirim")
        alertDialog.dismiss()
    }

    override fun gagalResetPass() {
        toast("Email reset pass gagal dikirim")
    }
}
