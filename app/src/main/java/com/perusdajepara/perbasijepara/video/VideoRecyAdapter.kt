package com.perusdajepara.perbasijepara.video

import android.arch.lifecycle.Lifecycle
import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import org.jetbrains.anko.doAsync
import android.support.annotation.NonNull
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.perusdajepara.perbasijepara.R
import kotlinx.android.synthetic.main.activity_detail_kategori_event.view.*
import kotlinx.android.synthetic.main.layout_video.view.*
import org.jetbrains.annotations.Nullable

class VideoRecyAdapter(options: FirebaseRecyclerOptions<VideoModel>, val listener: (VideoModel) -> Unit)
    : FirebaseRecyclerAdapter<VideoModel, VideoRecyAdapter.ViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_video, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: VideoModel) {
        holder.videoTitle.text = model.videoTitle?.toUpperCase()
        holder.itemView.setOnClickListener {
            listener(model)
        }
    }

    class ViewHolder(v: View): RecyclerView.ViewHolder(v) {
        val videoTitle: TextView = v.video_title
    }

}