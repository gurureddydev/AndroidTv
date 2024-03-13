package com.guru.androidtv.player

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.guru.androidtv.R
import com.guru.androidtv.databinding.ActivityVideoPlayerBinding

class VideoPlayerActivity : FragmentActivity() {
    private lateinit var binding: ActivityVideoPlayerBinding
    private lateinit var player: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_player)

        // Initialize ExoPlayer
        player = ExoPlayer.Builder(this).build() // Use ExoPlayer.Builder
        binding.playerView.player = player

        // Set up playback controls
        binding.playerControlView.player = player

        // Add media items to the playlist
        val mediaItem = MediaItem.fromUri("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4")
        player.setMediaItem(mediaItem)

        // Prepare the player
        player.prepare()

        // Start playback
        player.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}
