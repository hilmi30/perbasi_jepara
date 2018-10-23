package com.perusdajepara.perbasijepara.informasi.event


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerOptions

import com.perusdajepara.perbasijepara.R
import com.perusdajepara.perbasijepara.informasi.event.detailevent.DetailEventActivity
import com.perusdajepara.perbasijepara.utils.visible
import kotlinx.android.synthetic.main.fragment_event.*
import org.jetbrains.anko.support.v4.startActivity

class EventFragment : Fragment(), EventView {

    private lateinit var presenter: EventPresenter
    private lateinit var eventAdapter: EventRecyAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = EventPresenter()
        onAttachView()
    }

    override fun onAttachView() {
        presenter.onAttach(this)
        presenter.showListEvent()
    }

    override fun onDetachView() {
        presenter.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
        onDetachView()
    }

    override fun showList(options: FirebaseRecyclerOptions<EventModel>) {
        eventAdapter = EventRecyAdapter(options, event_loading) {
            startActivity<DetailEventActivity>("uid" to it)
        }

        val layoutManager = LinearLayoutManager(context)
        event_recy.layoutManager = layoutManager
        event_recy.adapter = eventAdapter
    }

    override fun showLoading() {
        event_loading.visible()
    }

    override fun onStart() {
        super.onStart()
        eventAdapter.startListening()
        showLoading()
    }

    override fun onStop() {
        super.onStop()
        eventAdapter.stopListening()
    }
}
