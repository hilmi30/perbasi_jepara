package com.perusdajepara.perbasijepara.listteam


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerOptions

import com.perusdajepara.perbasijepara.R
import com.perusdajepara.perbasijepara.model.TeamEventModel
import com.perusdajepara.perbasijepara.utils.gone
import com.perusdajepara.perbasijepara.utils.visible
import kotlinx.android.synthetic.main.fragment_team_event.*
import kotlinx.android.synthetic.main.layout_data_kosong.*
import kotlinx.android.synthetic.main.layout_loading.*
import org.jetbrains.anko.support.v4.toast

class ListTeamEventFragment : Fragment(), ListTeamEventView {

    private var presenter = ListTeamEventPresenter()
    private lateinit var adapter: ListTeamEventRecyAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team_event, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        onAttachView()
    }

    // ============== Setting Area

    override fun showList(options: FirebaseRecyclerOptions<TeamEventModel>) {
        val layoutManager = LinearLayoutManager(context)
        adapter = ListTeamEventRecyAdapter(options) {
            toast(it.nama.toString())
        }

        team_event_recy.layoutManager = layoutManager
        team_event_recy.adapter = adapter
    }

    override fun onAttachView() {
        presenter.onAttach(this)

        val uid = arguments?.getString("uid").toString()
        presenter.setTeamList(uid)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    // ============== Destroy Area

    override fun onDestroyView() {
        super.onDestroyView()
        onDetachView()
    }

    override fun onDetachView() {
        presenter.onDetach()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    // ============== Feedback Area

    override fun dataNotEmpty() {
        kosong.gone()
    }

    override fun dataEmpty() {
        kosong.visible()
    }

    override fun showLoading() {
        loading.visible()
    }

    override fun hideLoading() {
        loading.gone()
    }

    // ============== Error Area

    override fun errorCancelled() {
        toast(getString(R.string.terjadi_kesalahan))
    }
}
