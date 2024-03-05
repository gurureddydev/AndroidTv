package com.guru.androidtv.player

import android.net.Uri
import android.os.Bundle
import androidx.leanback.app.VideoSupportFragment
import androidx.leanback.app.VideoSupportFragmentGlueHost
import androidx.leanback.media.MediaPlayerAdapter
import androidx.leanback.media.PlaybackGlue
import androidx.leanback.media.PlaybackTransportControlGlue
import androidx.leanback.widget.PlaybackSeekDataProvider

class VideoFragment : VideoSupportFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val playerGlue = PlaybackTransportControlGlue(
            activity,
            MediaPlayerAdapter(activity)
        )
        playerGlue.host = VideoSupportFragmentGlueHost(this)
        playerGlue.addPlayerCallback(object : PlaybackGlue.PlayerCallback() {
            override fun onPreparedStateChanged(glue: PlaybackGlue) {
                if (glue.isPrepared == true) {
                    playerGlue.seekProvider = PlaybackSeekDataProvider()
                    playerGlue.play()
                }
            }
        })
        playerGlue.subtitle = "Leanback artist"
        playerGlue.title = "Leanback team at work"
        val uriPath = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
        playerGlue.playerAdapter.setDataSource(Uri.parse(uriPath))
    }
}
