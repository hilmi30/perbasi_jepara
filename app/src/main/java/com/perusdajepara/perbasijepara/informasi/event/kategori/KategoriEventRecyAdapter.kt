package com.perusdajepara.perbasijepara.informasi.event.kategori

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.perusdajepara.perbasijepara.R
import com.perusdajepara.perbasijepara.utils.gone
import com.perusdajepara.perbasijepara.utils.visible
import kotlinx.android.synthetic.main.layout_kategori_event.view.*
import org.jetbrains.anko.textColor

class KategoriEventRecyAdapter(options: FirebaseRecyclerOptions<KategoriEventModel>,
                               private val loading: ProgressBar, val listener: (KategoriEventModel) -> Unit)
    :FirebaseRecyclerAdapter<KategoriEventModel, KategoriEventRecyAdapter.KategoriEventViewHolder>(options){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KategoriEventViewHolder {
        return KategoriEventViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_kategori_event,
                parent, false))
    }

    override fun onBindViewHolder(holder: KategoriEventViewHolder, position: Int, model: KategoriEventModel) {

        loading.gone()

        val context = holder.itemView.context

        holder.kategoriJudul.text = model.nama
        holder.kategoriGender.text = model.keterangan

        when (model.status?.toString()) {
            "0" -> {
                holder.kategoriStatus.text = holder.itemView.context.getString(R.string.registrasi_belum_dibuka)
                holder.kategoriStatus.textColor = ContextCompat.getColor(
                        context, android.R.color.holo_red_dark)
                holder.kategoriRegister.text = context.getString(R.string.detail)
                holder.kategoriRegister.setOnClickListener {
                    listener(model)
                }
            }
            "1" -> {
                holder.kategoriStatus.text = holder.itemView.context.getString(R.string.registrasi_dibuka)
                holder.kategoriStatus.textColor = ContextCompat.getColor(
                        context, android.R.color.holo_green_dark)
                holder.kategoriRegister.text = context.getString(R.string.register)
                holder.kategoriRegister.setOnClickListener {
                    listener(model)
                }
            }
            "2" -> {
                holder.kategoriStatus.text = holder.itemView.context.getString(R.string.registrasi_ditutup)
                holder.kategoriStatus.textColor = ContextCompat.getColor(
                        context, android.R.color.holo_red_dark)
                holder.kategoriRegister.text = context.getString(R.string.detail)
                holder.kategoriRegister.setOnClickListener {
                    listener(model)
                }
            }
        }
    }

    class KategoriEventViewHolder(v: View): RecyclerView.ViewHolder(v) {
        val kategoriJudul: TextView = v.kategori_event_judul
        val kategoriGender: TextView = v.kategori_event_gender
        val kategoriStatus: TextView = v.kategori_event_status
        val kategoriRegister: TextView = v.kategori_event_register
    }
}