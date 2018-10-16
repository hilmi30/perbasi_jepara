package com.perusdajepara.perbasijepara.presenter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.perusdajepara.perbasijepara.R
import com.perusdajepara.perbasijepara.basecontract.BasePresenter
import com.perusdajepara.perbasijepara.fragment.EventFragment
import com.perusdajepara.perbasijepara.model.EventModel
import com.perusdajepara.perbasijepara.view.EventView

class EventPresenter: BasePresenter<EventView> {

    var mView: EventView? = null

    private lateinit var adapter: FirebaseRecyclerAdapter<EventModel, EventFragment.EventHolder>

    override fun onAttach(view: EventView) {
        mView = view
    }

    override fun onDetach() {
        mView = null
    }

    fun showListBerita() {
        val mDatabase = FirebaseDatabase.getInstance().reference

        val query = mDatabase.child("teslist")

        val options = FirebaseRecyclerOptions.Builder<EventModel>()
                .setQuery(query, EventModel::class.java).build()

        adapter = object : FirebaseRecyclerAdapter<EventModel, EventFragment.EventHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventFragment.EventHolder {
                return EventFragment.EventHolder(LayoutInflater.from(parent.context)
                        .inflate(R.layout.layout_event, parent, false))
            }

            override fun onBindViewHolder(holder: EventFragment.EventHolder, position: Int, model: EventModel) {
                mView?.setHolder(holder, position, model)
            }
        }

        mView?.showList(adapter)
    }

    fun startListening() {
        adapter.startListening()
    }

    fun stopListening() {
        adapter.stopListening()
    }
}