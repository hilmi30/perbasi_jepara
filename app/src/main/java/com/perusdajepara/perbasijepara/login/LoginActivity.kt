package com.perusdajepara.perbasijepara.login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.perusdajepara.perbasijepara.R
import com.perusdajepara.perbasijepara.activity.MainActivity
import com.perusdajepara.perbasijepara.signup.SignupActivity
import com.perusdajepara.perbasijepara.utils.gone
import com.perusdajepara.perbasijepara.utils.visible
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity(), LoginView {

    private lateinit var presenter: LoginPresenter

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
}
