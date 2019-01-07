package com.perusdajepara.perbasijepara.signup

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import com.perusdajepara.perbasijepara.R
import com.perusdajepara.perbasijepara.login.LoginActivity
import com.perusdajepara.perbasijepara.utils.*
import kotlinx.android.synthetic.main.activity_signup.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder
import kotlinx.android.synthetic.main.fragment_profile.*
import java.util.*


class SignupActivity : AppCompatActivity(), SignupView, com.tsongkha.spinnerdatepicker.DatePickerDialog.OnDateSetListener {

    private lateinit var presenter: SignupPresenter
    private var jenisKelamin: Int? = null
    private var tanggalLahir: String? = null

    private lateinit var namaLengkap: String
    private lateinit var pass: String
    private lateinit var passRepeat: String
    private lateinit var email: String
    private lateinit var alamat: String
    private lateinit var update: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        onAttachView()
    }

    override fun onAttachView() {
        presenter = SignupPresenter()
        presenter.onAttach(this)
        presenter.initFirebase()

        update = intent.getStringExtra(getString(R.string.update_profile))

        checkStatus()
        setCheckBoxGender()
        registerBtn()
        registerWithFacebook()
        setTanggalBtn()
    }

    private fun checkStatus() {

        when (update) {
            "1" -> {
                pass_edt.gone()
                ulangi_pass_edt.gone()
                email_edt.gone()
                register_btn.text = getString(R.string.update)

                val nama = intent.getStringExtra(getString(R.string.nama))
                val alamat = intent.getStringExtra(getString(R.string.alamat))
                val gender = intent.getStringExtra(getString(R.string.gender))
                tanggalLahir = intent.getStringExtra(getString(R.string.tanggalLahir))

                nama_lengkap_edt.setText(nama)
                alamat_edt.setText(alamat)
                tv_tanggal.text = tanggalLahir

                when (gender) {
                    "1" -> radio_male.isChecked = true
                    else -> radio_female.isChecked = true
                }
            }
            else -> {
                pass_edt.visible()
                ulangi_pass_edt.visible()
                email_edt.visible()
                register_btn.text = getString(R.string.register)
            }
        }
    }

    private fun setTanggalBtn() {
        btn_set_tanggal.setOnClickListener {
            SpinnerDatePickerDialogBuilder()
                    .context(this)
                    .callback(this)
                    .showTitle(true)
                    .showDaySpinner(true)
                    .defaultDate(
                            Calendar.getInstance().get(Calendar.YEAR),
                            Calendar.getInstance().get(Calendar.MONTH),
                            Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
                    .build()
                    .show()
        }
    }

    override fun onDateSet(view: com.tsongkha.spinnerdatepicker.DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        tanggalLahir = "$dayOfMonth/${monthOfYear+1}/$year"
        tv_tanggal.text = tanggalLahir
    }

    private fun setCheckBoxGender() {
        gender.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_male -> jenisKelamin = 1
                R.id.radio_female -> jenisKelamin = 0
            }
        }
    }

    private fun registerWithFacebook() {
        register_with_fb_btn.setOnClickListener {
            doRegisterWithFB()
        }
    }

    private fun doRegisterWithFB() {

    }

    private fun registerBtn() {
        register_btn.setOnClickListener {
            doRegister()
        }
    }

    private fun doRegister() {
        namaLengkap = nama_lengkap_edt.text.toString().trim()
        email = email_edt.text.toString().trim()
        pass = pass_edt.text.toString()
        passRepeat = ulangi_pass_edt.text.toString()
        alamat = alamat_edt.text.toString()

        when (update) {
            "1" -> if (validation()) presenter.updateProfile(namaLengkap, jenisKelamin, tanggalLahir, alamat)
            else -> if (validation()) presenter.register(namaLengkap, email, pass, jenisKelamin, tanggalLahir, alamat)
        }
    }

    private fun validation(): Boolean {

        var valid = true

        if (namaLengkap.isEmpty()) {
            nama_lengkap_edt.error = "nama tidak benar"
            valid = false
        }
        if (update == "0") {
            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                email_edt.error = "email tidak benar"
                valid = false
            }
            if (pass.isEmpty()) {
                pass_edt.error = "password tidak benar"
                valid = false
            }
            if (pass != passRepeat) {
                ulangi_pass_edt.error = "password tidak sama"
                valid = false
            }
            if (pass.length < 6) {
                pass_edt.error = "password harus lebih dari 6 karakter"
                valid = false
            }
        }
        if (tanggalLahir.isNullOrEmpty()) {
            toast("tanggal lahir tidak boleh kosong")
            valid = false
        }
        if (alamat.isEmpty()) {
            alamat_edt.error = "alamat tidak benar"
            valid = false
        }

        return valid
    }

    override fun onDetachView() {
        presenter.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
        onDetachView()
    }

    override fun errorNama() {
        nama_lengkap_edt.error = "nama tidak benar"
    }

    override fun errorEmail() {
        email_edt.error = "email tidak benar"
    }

    override fun errorPass() {
        pass_edt.error = "password tidak benar"
        ulangi_pass_edt.error = "password tidak benar"
    }

    override fun errorPassTidakSama() {
        pass_edt.error = "password tidak sama"
        ulangi_pass_edt.error = "password tidak sama"
    }

    override fun errorPassKurang() {
        pass_edt.error = "password harus lebih dari 6 karakter"
    }

    override fun failure() {
        toast("Terjadi kesalahan")
    }

    override fun successRegister() {
        startActivity<LoginActivity>()
        finish()
    }

    override fun showLoading() {
        register_progress.visible()
    }

    override fun hideLoading() {
        register_progress.gone()
    }

    override fun successUpdate() {
        finish()
    }
}
