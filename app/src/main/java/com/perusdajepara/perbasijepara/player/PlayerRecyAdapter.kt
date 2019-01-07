package com.perusdajepara.perbasijepara.player

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.perusdajepara.perbasijepara.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_player.view.*
import org.jetbrains.anko.textColor
import org.jetbrains.anko.textColorResource
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class PlayerRecyAdapter(options: FirebaseRecyclerOptions<PlayerModel>)
    : FirebaseRecyclerAdapter<PlayerModel, PlayerRecyAdapter.ViewHolder>(options){

    private var rank = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_player, parent, false))
    }

    override fun onDataChanged() {
        super.onDataChanged()
        rank = 0
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: PlayerModel) {

        rank += 1

        Picasso.get().load(model.thumbnail).placeholder(R.drawable.ic_launcher_background).into(holder.thumbPlayer)
        holder.namaPlayer.text = model.nama?.toUpperCase()
        holder.alamatPlayer.text = "${model.desa}, ${model.kecamatan}".toUpperCase()
        holder.teamPlayer.text = model.team?.toUpperCase()

        val point = model.point?.toDouble()

        val locale = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(locale)

        val decimalFormatSymbols = (formatRupiah as DecimalFormat).decimalFormatSymbols
        decimalFormatSymbols.currencySymbol = ""
        formatRupiah.decimalFormatSymbols = decimalFormatSymbols

        holder.pointPlayer.text = "Points : ${formatRupiah.format(point)}"

        holder.rankPlayer.text = "Rank : $rank"
    }

    class ViewHolder(v: View): RecyclerView.ViewHolder(v) {
        val thumbPlayer: ImageView = v.image_player
        val namaPlayer: TextView = v.nama_player
        val alamatPlayer: TextView = v.alamat_player
        val teamPlayer: TextView = v.team_player
        val pointPlayer: TextView = v.point_player
        val rankPlayer: TextView = v.rank_player
    }
}