package com.perusdajepara.perbasijepara.listberita

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.perusdajepara.perbasijepara.R
import com.perusdajepara.perbasijepara.model.BeritaModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_berita.view.*

class ListBeritaRecyAdapter(options: FirebaseRecyclerOptions<BeritaModel>,
                            val listener: (String) -> Unit)
    : FirebaseRecyclerAdapter<BeritaModel, ListBeritaRecyAdapter.BeritaHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeritaHolder {
        return BeritaHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_berita, parent, false))
    }

    override fun onBindViewHolder(holder: BeritaHolder, position: Int, model: BeritaModel) {

        holder.titleBerita.text = model.judul?.toUpperCase()
        Picasso.get().load(model.thumbnail).into(holder.gambarBerita)

        holder.itemView.setOnClickListener {
            val uid = getRef(position).key.toString()
            listener(uid)
        }
    }

    class BeritaHolder(v: View): RecyclerView.ViewHolder(v) {
        val titleBerita: TextView = v.berita_judul
        val gambarBerita: ImageView = v.berita_gambar
    }
}