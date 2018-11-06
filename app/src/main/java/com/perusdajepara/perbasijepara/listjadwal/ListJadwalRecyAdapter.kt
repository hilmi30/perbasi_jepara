package com.perusdajepara.perbasijepara.listjadwal

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.perusdajepara.perbasijepara.R
import com.perusdajepara.perbasijepara.model.JadwalModel
import kotlinx.android.synthetic.main.layout_jadwal.view.*

class ListJadwalRecyAdapter(options: FirebaseRecyclerOptions<JadwalModel>)
    : FirebaseRecyclerAdapter<JadwalModel, ListJadwalRecyAdapter.ViewHolder>(options){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_jadwal, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: JadwalModel) {
        holder.teamA.text = model.teamA
        holder.teamB.text = model.teamB
        holder.jadwalTanggal.text = model.tanggal
        holder.jadwalWaktu.text = model.waktu
    }

    class ViewHolder(v: View): RecyclerView.ViewHolder(v) {
        val teamA: TextView = v.team_a
        val teamB: TextView = v.team_b
        val jadwalWaktu: TextView = v.jadwal_waktu
        val jadwalTanggal: TextView = v.jadwal_tanggal
    }
}