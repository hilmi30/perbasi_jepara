package com.perusdajepara.perbasijepara.listteam

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.perusdajepara.perbasijepara.R
import com.perusdajepara.perbasijepara.model.TeamEventModel
import kotlinx.android.synthetic.main.layout_event_teams.view.*

class ListTeamEventRecyAdapter(options: FirebaseRecyclerOptions<TeamEventModel>,
                               val listener: (TeamEventModel) -> Unit)
    : FirebaseRecyclerAdapter<TeamEventModel, ListTeamEventRecyAdapter.TeamEventViewHolder>(options){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamEventViewHolder {
        return TeamEventViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_event_teams,
                parent, false))
    }

    override fun onBindViewHolder(holder: TeamEventViewHolder, position: Int, model: TeamEventModel) {
        holder.namaTeam.text = model.nama
        holder.itemView.setOnClickListener {
            listener(model)
        }
    }

    class TeamEventViewHolder(v: View): RecyclerView.ViewHolder(v) {
        val namaTeam: TextView = v.nama_team
    }
}