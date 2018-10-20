package com.perusdajepara.perbasijepara.berita

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.perusdajepara.perbasijepara.R
import com.perusdajepara.perbasijepara.utils.gone
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_berita.view.*

class BeritaRecyAdapter(options: FirebaseRecyclerOptions<BeritaModel>, val berita_loading: ProgressBar,
                        val listener: (BeritaModel) -> Unit)
    : FirebaseRecyclerAdapter<BeritaModel, BeritaRecyAdapter.BeritaHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeritaHolder {
        return BeritaHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_berita, parent, false))
    }

    override fun onBindViewHolder(holder: BeritaHolder, position: Int, model: BeritaModel) {

        berita_loading.gone()

        holder.titleBerita.text = model.judul?.toUpperCase()
        Picasso.get().load(model.thumbnail).into(holder.gambarBerita)

        holder.itemView.setOnClickListener {
            listener(model)
        }
    }

    class BeritaHolder(v: View): RecyclerView.ViewHolder(v) {
        val titleBerita: TextView = v.berita_judul
        val gambarBerita: ImageView = v.berita_gambar
    }
}