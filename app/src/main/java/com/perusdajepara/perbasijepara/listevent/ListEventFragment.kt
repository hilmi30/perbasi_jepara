package com.perusdajepara.perbasijepara.listevent


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerOptions

import com.perusdajepara.perbasijepara.R
import com.perusdajepara.perbasijepara.detailevent.DetailEventActivity
import com.perusdajepara.perbasijepara.model.EventModel
import com.perusdajepara.perbasijepara.utils.gone
import com.perusdajepara.perbasijepara.utils.visible
import kotlinx.android.synthetic.main.fragment_event.*
import kotlinx.android.synthetic.main.layout_data_kosong.*
import kotlinx.android.synthetic.main.layout_loading.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class ListEventFragment : Fragment(), ListEventView {




    private lateinit var presenter: ListEventPresenter
    private lateinit var eventAdapter: ListEventRecyAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = ListEventPresenter()
        onAttachView()
    }

    // =========== Setting Area

    override fun onAttachView() {
        presenter.onAttach(this)
        presenter.showListEvent()
    }

    override fun showList(options: FirebaseRecyclerOptions<EventModel>) {
        eventAdapter = ListEventRecyAdapter(options) {
            startActivity<DetailEventActivity>("uid" to it)
        }

        val layoutManager = LinearLayoutManager(context)
        event_recy.layoutManager = layoutManager
        event_recy.adapter = eventAdapter
    }

    override fun onStart() {
        super.onStart()
        eventAdapter.startListening()
    }

    // =========== Destroy Area

    override fun onDetachView() {
        presenter.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
        onDetachView()
    }

    override fun onStop() {
        super.onStop()
        eventAdapter.stopListening()
    }

    // =========== Feedback Area

    override fun dataNotEmpty() {
        kosong.gone()
    }

    override fun dataEmpty() {
        kosong.visible()
    }

    override fun hideLoading() {
        loading.gone()
    }

    override fun showLoading() {
        loading.visible()
    }

    // =========== Error Area

    override fun errorCancelled() {
        toast(getString(R.string.terjadi_kesalahan))
    }
}
