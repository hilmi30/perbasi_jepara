package com.perusdajepara.perbasijepara.liststandings

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.perusdajepara.perbasijepara.R
import com.perusdajepara.perbasijepara.model.StandingsModel
import kotlinx.android.synthetic.main.layout_standings.view.*

class ListStandingsRecyAdapter(options: FirebaseRecyclerOptions<StandingsModel>)
    : FirebaseRecyclerAdapter<StandingsModel, ListStandingsRecyAdapter.ViewHolder>(options){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_standings, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: StandingsModel) {
        holder.namaTeam.text = model.team
        holder.winLose.text = "${model.win} - ${model.lose}"
        holder.pts.text = model.points.toString()
        holder.avgPts.text = model.avgPoints.toString()
    }

    class ViewHolder(v: View): RecyclerView.ViewHolder(v) {
        val namaTeam: TextView = v.standings_nama_team
        val winLose: TextView = v.standing_win_lose
        val pts: TextView = v.standings_pts
        val avgPts: TextView = v.standings_avg_pts
    }
}