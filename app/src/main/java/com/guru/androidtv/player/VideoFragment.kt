package com.guru.androidtv.player


import android.net.Uri
import android.os.Bundle
import androidx.leanback.app.VideoSupportFragment
import androidx.leanback.app.VideoSupportFragmentGlueHost

class VideoFragment : VideoSupportFragment() {

    companion object {
        const val TITLE = "Leanback artist"
        const val SUB_TITLE = "Leanback team at work"
        const val URI_PATH =
            "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
    }

    private lateinit var transportGlue: CustomTransportControlGlue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        transportGlue = CustomTransportControlGlue(
            context = requireContext(),
            playerAdapter = BasicMediaPlayerAdapter(requireContext())
        )

        transportGlue.host = VideoSupportFragmentGlueHost(this)

        transportGlue.apply {
            subtitle = TITLE
            title = SUB_TITLE
            playerAdapter.setDataSource(Uri.parse(URI_PATH))
        }
    }
}
