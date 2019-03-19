package com.perusdajepara.perbasijepara.registerevent


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.perusdajepara.perbasijepara.R
import com.perusdajepara.perbasijepara.utils.gone
import com.perusdajepara.perbasijepara.utils.visible
import kotlinx.android.synthetic.main.fragment_register_event.*
import org.jetbrains.anko.okButton
import org.jetbrains.anko.support.v4.alert


class RegisterEventFragment : Fragment(), RegisterEventView {

    private val presenter = RegisterEventPresenter()
    private lateinit var uidEvent: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_event, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        uidEvent = arguments?.getString("uid").toString()
        onAttachView()
    }

    override fun onAttachView() {
        presenter.onAttach(this)
        presenter.initFirebase()

        setKirimBtn()
    }

    private fun setKirimBtn() {
        btn_kirim.setOnClickListener {
            val namaTeam = edt_nama_team.text.toString()
            val emailTeam = edt_email.text.toString()
            val noTelp = edt_nmr_tlp.text.toString()

            if (validation()) presenter.kirimDaftarEvent(namaTeam, emailTeam, noTelp, uidEvent)
        }
    }

    private fun validation(): Boolean {
        var valid = true

        if (edt_nama_team.text.isEmpty()) {
            edt_nama_team.error = "tidak boleh kosong"
            valid = false
        }
        if (edt_email.text.isEmpty()) {
            edt_email.error = "tidak boleh kosong"
            valid = false
        }
        if (edt_nmr_tlp.text.isEmpty()) {
            edt_nmr_tlp.error = "tidak boleh kosong"
            valid = false
        }

        return valid
    }

    override fun onDetachView() {
        presenter.onDetach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onDetachView()
    }

    override fun hideLoading() {
        pb_register_event.gone()
    }

    override fun daftarEventBerhasil() {
        alert {
            message = "Daftar Event Berhasil"
            okButton {
                it.dismiss()
            }
        }.show()
    }

    override fun gagalDaftarEvent() {
        alert {
            message = "Daftar Event Gagal, silahkan coba lagi"
            okButton {
                it.dismiss()
            }
        }.show()
    }

    override fun showLoading() {
        pb_register_event.visible()
    }

}
