package com.guru.androidtv.player

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.GestureDetector
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.isVisible
import androidx.leanback.app.VideoSupportFragment
import androidx.leanback.app.VideoSupportFragmentGlueHost
import com.guru.androidtv.R
import com.guru.androidtv.model.DetailResponse

class VideoFragment : VideoSupportFragment() {

    companion object {
        const val TITLE = "Leanback artist"
        const val SUB_TITLE = "Leanback team at work"
        const val URI_PATH =
            "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"
    }

    private val SWIPE_THRESHOLD = 100
    private val SEEK_FORWARD_INTERVAL = 10000 // 10 seconds
    private val SEEK_BACKWARD_INTERVAL = 10000 // 10 seconds

    private lateinit var transportGlue: CustomTransportControlGlue
    private lateinit var fastForwardIndicatorView: View
    private lateinit var rewindIndicatorView: View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializePlayer()
    }

    private fun initializePlayer() {
        val detailResponse = getDetailResponse()
        transportGlue = createTransportControlGlue(detailResponse)
        setOnKeyInterceptListener { _, keyCode, event ->
            handleKeyEvents(keyCode, event)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = setupView(inflater, container, savedInstanceState)
        setupTouchControls(view)
        return view
    }

    private fun setupTouchControls(view: View) {
        view.setOnTouchListener(object : View.OnTouchListener {
            private val gestureDetector = GestureDetectorCompat(requireContext(), object :
                GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                    togglePlayPause()
                    return true
                }

                override fun onScroll(
                    e1: MotionEvent?,
                    e2: MotionEvent,
                    distanceX: Float,
                    distanceY: Float
                ): Boolean {
                    handleScroll(e1, e2, distanceX, distanceY)
                    return true
                }
            })

            override fun onTouch(p0: View?, p1: MotionEvent): Boolean {
                return gestureDetector.onTouchEvent(p1)
            }
        })
    }


    private fun getDetailResponse(): DetailResponse? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("movie_detail", DetailResponse::class.java)
        } else {
            arguments?.getParcelable("movie_detail")
        }
    }

    private fun createTransportControlGlue(detailResponse: DetailResponse?): CustomTransportControlGlue {
        val glue = CustomTransportControlGlue(
            context = requireContext(),
            playerAdapter = BasicMediaPlayerAdapter(requireContext())
        )

        glue.loadMovieInfo(detailResponse)

        glue.host = VideoSupportFragmentGlueHost(this)

        glue.apply {
            subtitle = TITLE
            title = SUB_TITLE
            playerAdapter.setDataSource(Uri.parse(URI_PATH))
        }

        return glue
    }

    private fun setupView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ViewGroup {
        val view = super.onCreateView(inflater, container, savedInstanceState) as ViewGroup

        fastForwardIndicatorView = inflater.inflate(R.layout.view_forward, view, false)
        view.addView(fastForwardIndicatorView)

        rewindIndicatorView = inflater.inflate(R.layout.view_rewind, view, false)
        view.addView(rewindIndicatorView)

        return view
    }

//    private fun setupGestureDetector(view: ViewGroup) {
//        view.setOnTouchListener(object : View.OnTouchListener {
//            private val gestureDetector = GestureDetectorCompat(requireContext(), object :
//                GestureDetector.SimpleOnGestureListener() {
//                override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
//                    togglePlayPause()
//                    return true
//                }
//
//                override fun onScroll(
//                    e1: MotionEvent?,
//                    e2: MotionEvent,
//                    distanceX: Float,
//                    distanceY: Float
//                ): Boolean {
//                    handleScroll(e1, e2, distanceX, distanceY)
//                    return true
//                }
//            })
//
//            override fun onTouch(p0: View?, p1: MotionEvent): Boolean {
//                return gestureDetector.onTouchEvent(p1)
//            }
//        })
//    }

    private fun handleKeyEvents(keyCode: Int, event: KeyEvent?): Boolean {
        if (isControlsOverlayVisible || (event?.repeatCount ?: 0) > 0) {
            isShowOrHideControlsOverlayOnUserInteraction = true
            return false
        }

        when (keyCode) {
            KeyEvent.KEYCODE_DPAD_RIGHT -> {
                if (event?.action == KeyEvent.ACTION_DOWN) {
                    animateIndicator(fastForwardIndicatorView)
                }
                return true
            }

            KeyEvent.KEYCODE_DPAD_LEFT -> {
                if (event?.action == KeyEvent.ACTION_DOWN) {
                    animateIndicator(rewindIndicatorView)
                }
                return true
            }

            else -> return transportGlue.onKey(view, keyCode, event)
        }
    }

    private fun togglePlayPause() {
        if (transportGlue.isPlaying) {
            transportGlue.pause()
        } else {
            transportGlue.play()
        }
    }

    private fun handleScroll(
        e1: MotionEvent?,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ) {
        val deltaX = e1?.x?.minus(e2.x) ?: 0f
        if (Math.abs(deltaX) > SWIPE_THRESHOLD) {
            if (deltaX < 0) {
                seekForward()
            } else {
                seekBackward()
            }
        }
    }

    private fun seekForward() {
        transportGlue.seekTo(transportGlue.currentPosition + SEEK_FORWARD_INTERVAL)
    }

    private fun seekBackward() {
        transportGlue.seekTo(transportGlue.currentPosition - SEEK_BACKWARD_INTERVAL)
    }

    private fun animateIndicator(indicator: View) {
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

    override fun onStart() {
        super.onStart()
        startPlayer()
    }

    private fun startPlayer() {
        // Start or resume the video playback.
        transportGlue.play()
    }

    override fun onStop() {
        super.onStop()
        stopPlayer()
    }

    private fun stopPlayer() {
        // Pause or stop the video playback.
        transportGlue.pause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        destroyPlayer()
    }

    private fun destroyPlayer() {
        // Release resources and clean up the video player.
        // You might want to release the player, detach listeners, etc.
    }
}
