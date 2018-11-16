package com.perusdajepara.perbasijepara.foto

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.perusdajepara.perbasijepara.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_foto.view.*

class FotoAdapter(options: FirebaseRecyclerOptions<FotoModel>, val listener: (FotoModel) -> Unit)
    : FirebaseRecyclerAdapter<FotoModel, FotoAdapter.ViewHolder>(options) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: FotoModel) {
        Picasso.get().load(model.imageUrl).into(holder.img)
        holder.img.setOnClickListener {
            listener(model)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_foto, parent, false))
    }

    class ViewHolder(v: View): RecyclerView.ViewHolder(v) {
        val img: ImageView = v.foto_img
    }

}