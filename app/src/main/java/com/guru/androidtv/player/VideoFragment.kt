package com.guru.androidtv.player


import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.view.isVisible
import androidx.leanback.app.VideoSupportFragment
import androidx.leanback.app.VideoSupportFragmentGlueHost
import com.guru.androidtv.R

class VideoFragment : VideoSupportFragment() {

    companion object {
        const val TITLE = "Leanback artist"
        const val SUB_TITLE = "Leanback team at work"
        const val URI_PATH =
            "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
    }

    private lateinit var transportGlue: CustomTransportControlGlue
    private lateinit var fastForwardIndicatorView: View
    private lateinit var rewindIndicatorView: View


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

        setOnKeyInterceptListener { view, keyCode, event ->
            if (isControlsOverlayVisible || event.repeatCount > 0) {
                isShowOrHideControlsOverlayOnUserInteraction = true
            } else when (keyCode) {
                KeyEvent.KEYCODE_DPAD_RIGHT -> {
                    isShowOrHideControlsOverlayOnUserInteraction =
                        event.action != KeyEvent.ACTION_DOWN
                    if (event.action == KeyEvent.ACTION_DOWN) {
                        animateIndicator(fastForwardIndicatorView)
                    }
                }

                KeyEvent.KEYCODE_DPAD_LEFT -> {
                    isShowOrHideControlsOverlayOnUserInteraction =
                        event.action != KeyEvent.ACTION_DOWN
                    if (event.action == KeyEvent.ACTION_DOWN) {
                        animateIndicator(rewindIndicatorView)

                    }
                }
            }
            transportGlue.onKey(view, keyCode, event)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState) as ViewGroup

        fastForwardIndicatorView = inflater.inflate(R.layout.view_forward, view, false)
        view.addView(fastForwardIndicatorView)

        rewindIndicatorView = inflater.inflate(R.layout.view_rewind, view, false)
        view.addView(rewindIndicatorView)

        return view
    }

    fun animateIndicator(indicator: View) {
        indicator.animate()
            .withEndAction {
                indicator.isVisible = false
                indicator.alpha = 1f
                indicator.scaleX = 1f
                indicator.scaleY = 1f
            }
            .withStartAction {
                indicator.isVisible = true
            }
            .alpha(0.2f)
            .scaleX(2f)
            .scaleY(2f)
            .setDuration(400)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()
    }
}
