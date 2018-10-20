package com.perusdajepara.perbasijepara.event

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.perusdajepara.perbasijepara.R
import com.perusdajepara.perbasijepara.model.KategoriEventModel
import com.perusdajepara.perbasijepara.utils.invisible
import com.perusdajepara.perbasijepara.utils.visible
import kotlinx.android.synthetic.main.layout_kategori_event.view.*
import org.jetbrains.anko.textColor

class KategoriEventRecyAdapter(options: FirebaseRecyclerOptions<KategoriEventModel>)
    :FirebaseRecyclerAdapter<KategoriEventModel, KategoriEventRecyAdapter.ViewHolder>(options){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_kategori_event,
                parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: KategoriEventModel) {
        holder.kategoriJudul.text = model.nama
        holder.kategoriGender.text = model.gender.toString()

        when (model.status?.toInt()) {
            0 -> {
                holder.kategoriStatus.text = holder.itemView.context.getString(R.string.registrasi_belum_dibuka)
                holder.kategoriStatus.textColor = android.R.color.holo_orange_dark
                holder.kategoriRegister.invisible()
            }
            1 -> {
                holder.kategoriStatus.text = holder.itemView.context.getString(R.string.registrasi_dibuka)
                holder.kategoriStatus.textColor = android.R.color.holo_green_dark
                holder.kategoriRegister.visible()
            }
            2 -> {
                holder.kategoriStatus.text = holder.itemView.context.getString(R.string.registrasi_ditutup)
                holder.kategoriStatus.textColor = android.R.color.holo_red_dark
                holder.kategoriRegister.invisible()
            }
        }
    }

    class ViewHolder(v: View): RecyclerView.ViewHolder(v) {
        val kategoriJudul: TextView = v.kategori_event_judul
        val kategoriGender: TextView = v.kategori_event_gender
        val kategoriStatus: TextView = v.kategori_event_status
        val kategoriRegister: TextView = v.kategori_event_register
    }
}