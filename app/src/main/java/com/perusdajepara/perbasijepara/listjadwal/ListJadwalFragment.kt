package com.perusdajepara.perbasijepara.listjadwal


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerOptions

import com.perusdajepara.perbasijepara.R
import com.perusdajepara.perbasijepara.model.JadwalModel
import com.perusdajepara.perbasijepara.utils.gone
import com.perusdajepara.perbasijepara.utils.visible
import kotlinx.android.synthetic.main.fragment_jadwal.*
import kotlinx.android.synthetic.main.layout_data_kosong.*
import kotlinx.android.synthetic.main.layout_loading.*
import org.jetbrains.anko.support.v4.toast

class ListJadwalFragment : Fragment(), ListJadwalView {

    private val presenter = ListJadwalPresenter()
    private lateinit var adapter: ListJadwalRecyAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_jadwal, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        onAttachView()
    }

    // =========== Setting Area

    override fun onAttachView() {
        presenter.onAttach(this)

        val uid = arguments?.getString("uid").toString()
        presenter.setListJadwal(uid)
    }

    override fun showList(options: FirebaseRecyclerOptions<JadwalModel>) {
        adapter = ListJadwalRecyAdapter(options)
        jadwal_recy.layoutManager = LinearLayoutManager(context)
        jadwal_recy.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    // =========== Destroy Area

    override fun onDetachView() {
        presenter.onDetach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onDetachView()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    // =========== Feedback Area

    override fun showLoading() {
        loading.visible()
    }

    override fun hideLoading() {
        loading.gone()
    }

    override fun dataEmpty() {
        kosong.visible()
    }

    override fun dataNotEmpty() {
        kosong.gone()
    }

    // =========== Error Area

    override fun errorCancelled() {
        toast(getString(R.string.terjadi_kesalahan))
    }

}
