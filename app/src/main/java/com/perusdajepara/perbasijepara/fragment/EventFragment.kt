package com.perusdajepara.perbasijepara.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.firebase.ui.database.FirebaseRecyclerAdapter

import com.perusdajepara.perbasijepara.R
import com.perusdajepara.perbasijepara.activity.DetailBeritaActivity
import com.perusdajepara.perbasijepara.activity.DetailEventActivity
import com.perusdajepara.perbasijepara.model.EventModel
import com.perusdajepara.perbasijepara.presenter.BeritaPresenter
import com.perusdajepara.perbasijepara.presenter.EventPresenter
import com.perusdajepara.perbasijepara.view.EventView
import kotlinx.android.synthetic.main.fragment_berita.*
import kotlinx.android.synthetic.main.fragment_event.*
import kotlinx.android.synthetic.main.layout_berita.view.*
import kotlinx.android.synthetic.main.layout_event.view.*
import org.jetbrains.anko.support.v4.startActivity

class EventFragment : Fragment(), EventView {

    private lateinit var presenter: EventPresenter

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
        presenter.showListBerita()
        presenter.startListening()
    }

    override fun onDetachView() {
        presenter.onDetach()
        presenter.stopListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        onDetachView()
    }

    override fun setHolder(holder: EventHolder, position: Int, model: EventModel) {
        holder.titleEvent.text = model.title
        holder.itemView.setOnClickListener {
            startActivity<DetailEventActivity>()
        }
    }

    override fun showList(adapter: FirebaseRecyclerAdapter<EventModel, EventHolder>) {
        val layoutManager = LinearLayoutManager(context)
        event_recy.layoutManager = layoutManager
        event_recy.adapter = adapter
    }

    class EventHolder(v: View): RecyclerView.ViewHolder(v) {
        val titleEvent: TextView = v.event_judul
    }
}
