package com.guru.androidtv.player

import android.os.Bundle
import android.util.SparseArray
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.MergingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.Util
import com.guru.androidtv.R
import com.guru.androidtv.databinding.ActivityVideoPlayerBinding

class VideoPlayerActivity : FragmentActivity() {
    private lateinit var binding: ActivityVideoPlayerBinding
    private  var player: ExoPlayer? = null
    private var playbackPosition: Long = 0
    private var playerWhenReady = true
    private var currentWindow = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_player)


        // Add media items to the playlist
//        val mediaItem =
//            MediaItem.fromUri("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4")
//        player.setMediaItem(mediaItem)

        // Initialize lifecycle observer
        initPlayer()
    }

    private fun initPlayer() {
        // Initialize ExoPlayer
        player = ExoPlayer.Builder(this).build() // Use ExoPlayer.Builder
        binding.playerView.player = player

        // Set up playback controls

        val videoUrl = "https://www.youtube.com/watch?v=v2H1s9gj5DA"

        object : YouTubeExtractor(this) {
            override fun onExtractionComplete(
                ytFiles: SparseArray<YtFile>?,
                videoMeta: VideoMeta?
            ) {
                if (ytFiles != null) {
                    val itag = 22
                    val audioTag = 140
                    val videoUrl = ytFiles[itag].url
                    val audioUrl = ytFiles[audioTag].url

                    val audioSource: MediaSource = ProgressiveMediaSource
                        .Factory(DefaultHttpDataSource.Factory())
                        .createMediaSource(MediaItem.fromUri(audioUrl))

                    val videoSource: MediaSource = ProgressiveMediaSource
                        .Factory(DefaultHttpDataSource.Factory())
                        .createMediaSource(MediaItem.fromUri(videoUrl))

                    player?.setMediaSource(
                        MergingMediaSource(
                            true, videoSource, audioSource
                        ), true
                    )
                    player?.prepare()
                    player?.playWhenReady = playerWhenReady
                    player?.seekTo(currentWindow, playbackPosition)
                }
            }

        }.extract(videoUrl, false, true)

    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initPlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT < 24 || player == null) {
            initPlayer()
            hideSystemUi()
        }
        // Restore playback position and start playback
//        player?.seekTo(playbackPosition)
//        player?.play()
    }

    override fun onPause() {
        if(Util.SDK_INT<24 ) releasePlayer()
        super.onPause()
        // Pause playback and save playback position
//        player?.pause()
//        playbackPosition = player?.currentPosition!!
    }

    override fun onStop() {
        if(Util.SDK_INT<24 ) releasePlayer()
        super.onStop()
    }

    private fun releasePlayer() {
        if(player != null){
            playerWhenReady = player?.playWhenReady == true
            playbackPosition = player?.currentPosition!!
            currentWindow = player?.currentWindowIndex!!
            player?.release()
            player = null
        }
    }

    private fun hideSystemUi() {
        binding.playerView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LOW_PROFILE or
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                )
    }

}
