package com.perusdajepara.perbasijepara.listevent

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.perusdajepara.perbasijepara.R
import com.perusdajepara.perbasijepara.model.EventModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_event.view.*

class ListEventRecyAdapter(options: FirebaseRecyclerOptions<EventModel>,
                           val listener: (String) -> Unit)
    : FirebaseRecyclerAdapter<EventModel, ListEventRecyAdapter.EventHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        return EventHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_event, parent, false))
    }

    override fun onBindViewHolder(holder: EventHolder, position: Int, model: EventModel) {
        holder.titleEvent.text = model.judul?.toUpperCase()
        Picasso.get().load(model.thumbnail).into(holder.eventGambar)
        holder.lokasiEvent.text = model.lokasi
        holder.itemView.setOnClickListener {
            val uid = getRef(position).key.toString()
            listener(uid)
        }
    }

    class EventHolder(v: View): RecyclerView.ViewHolder(v) {
        val titleEvent: TextView = v.event_judul
        val tanggalEvent: TextView = v.event_tanggal
        val eventGambar: ImageView = v.event_gambar
        val lokasiEvent: TextView = v.event_lokasi
    }
}