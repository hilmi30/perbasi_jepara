package com.perusdajepara.perbasijepara.videoplayer

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.perusdajepara.perbasijepara.R
import kotlinx.android.synthetic.main.activity_video_player.*
import org.jetbrains.anko.alert

class VideoPlayerActivity : AppCompatActivity() {

    private var player: SimpleExoPlayer? = null
    private var playWhenReady: Boolean = true
    private var videoUrl: String? = null
    private var videoTitle: String? = null
    private var playbackPosition: Long = 0
    private var currentWindow: Int = 0
    private var isAgree: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)
        setSupportActionBar(video_player_toolbar)
        video_player_toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)

        videoUrl = intent.getStringExtra("videoUrl")
        videoTitle = intent.getStringExtra("videoTitle")

        video_title.text = videoTitle
    }

    override fun onStart() {
        super.onStart()

        if(isAgree) {
            initializePlayer()
        } else {
            alert {
                title = "Streaming Video"
                message = "Diperlukan data internet untuk memutar video. Lanjutkan?"

                positiveButton("Ya") {
                    initializePlayer()
                    isAgree = true
                }
                negativeButton("Tidak") {
                    finish()
                }
            }.show()
        }
    }

    private fun initializePlayer() {

        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(
                    DefaultRenderersFactory(this),
                    DefaultTrackSelector(),
                    DefaultLoadControl())
            exo_player?.player = player
            player?.playWhenReady = playWhenReady
            player?.seekTo(currentWindow, playbackPosition)
        }

        val mediaSource = buildMediaSource(Uri.parse(videoUrl))
        player?.prepare(mediaSource, false, false)
    }

    private fun buildMediaSource(uri: Uri?): MediaSource {

        val userAgent = "perbasijepara"

        return if (uri?.lastPathSegment?.contains("mp3")!! || uri.lastPathSegment.contains("mp4")) {
            ExtractorMediaSource.Factory(DefaultHttpDataSourceFactory(userAgent))
                    .createMediaSource(uri)
        } else if (uri.lastPathSegment.contains("m3u8")) {
            HlsMediaSource.Factory(DefaultHttpDataSourceFactory(userAgent))
                    .createMediaSource(uri)
        } else {
            val dashChunkSourceFactory = DefaultDashChunkSource.Factory(
                    DefaultHttpDataSourceFactory(userAgent))
            val manifestDataSourceFactory = DefaultHttpDataSourceFactory(userAgent)
            DashMediaSource.Factory(dashChunkSourceFactory, manifestDataSourceFactory).createMediaSource(uri)
        }
    }

    private fun releasePlayer() {
        if (player != null) {
            playbackPosition = player?.currentPosition!!
            currentWindow = player?.currentWindowIndex!!
            playWhenReady = player?.playWhenReady!!
            player?.release()
            player = null
        }
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return true
    }
}
