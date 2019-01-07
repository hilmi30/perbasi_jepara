package com.perusdajepara.perbasijepara.video


import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector

import com.perusdajepara.perbasijepara.R
import kotlinx.android.synthetic.main.fragment_video.*
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.util.Util
import android.media.MediaMetadataRetriever
import android.os.Build
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.media.session.PlaybackState
import android.provider.MediaStore
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.AbsListView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.firebase.database.FirebaseDatabase
import com.perusdajepara.perbasijepara.utils.gone
import com.perusdajepara.perbasijepara.utils.visible
import com.perusdajepara.perbasijepara.videoplayer.VideoPlayerActivity
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast


class VideoFragment : Fragment(), VideoView {

    private lateinit var videoAdapter: VideoRecyAdapter
    private val presenter = VideoPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        onAttachView()
    }

    // SET AREA //
    override fun onAttachView() {
        presenter.onAttach(this)
        presenter.setListVideo()
    }

    override fun setToAdapter(options: FirebaseRecyclerOptions<VideoModel>) {
        video_recy.layoutManager = LinearLayoutManager(context)
        videoAdapter = VideoRecyAdapter(options) {
            startActivity<VideoPlayerActivity>(
                    "videoUrl" to it.videoUrl,
                    "videoTitle" to it.videoTitle
            )
        }

        video_recy.adapter = videoAdapter
    }

    override fun onStart() {
        super.onStart()
        videoAdapter.startListening()
    }

    // DESTROY AREA //
    override fun onDetachView() {
        presenter.onDetach()
    }

    override fun onStop() {
        super.onStop()
        videoAdapter.stopListening()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onDetach()
    }

    // ERROR AREA //
    override fun errorCancelled() {
        toast(getString(R.string.terjadi_kesalahan))
    }

    // FEEDBACK AREA //
    override fun hideLoading() {
        loading_video.gone()
    }

    override fun showLoading() {
        loading_video.visible()
    }

    override fun dataEmpty() {
        data_kosong_video.visible()
    }

    override fun dataNotEmpty() {
        data_kosong_video.gone()
    }
}
