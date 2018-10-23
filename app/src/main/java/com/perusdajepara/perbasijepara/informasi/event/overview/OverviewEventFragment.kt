package com.perusdajepara.perbasijepara.informasi.event.overview


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.perusdajepara.perbasijepara.R
import com.perusdajepara.perbasijepara.informasi.event.EventModel
import kotlinx.android.synthetic.main.fragment_overview_event.*
import org.jetbrains.anko.support.v4.toast

class OverviewEventFragment : Fragment(), OverviewEventView {

    private lateinit var presenter: OverviewEventPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_overview_event, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = OverviewEventPresenter()
        onAttachView()
    }

    override fun onStop() {
        super.onStop()
        presenter.removeListener()
    }

    override fun onAttachView() {
        presenter.onAttach(this)

        val eventUid = arguments?.getString("eventUid").toString()
        presenter.setOverviewItem(eventUid)

    }

    override fun onDetachView() {
        presenter.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
        onDetachView()
    }

    override fun setDataError() {
        toast("Terjadi Kesalahan")
    }

    override fun setData(eventModel: EventModel) {
        event_lokasi.text = eventModel.lokasi
        event_alamat.text = eventModel.alamat
        event_tanggal.text = eventModel.tanggal
        event_team_terdaftar.text = eventModel.teamTerdaftar.toString()
        event_deskripsi.text = eventModel.deskripsi
    }
}
