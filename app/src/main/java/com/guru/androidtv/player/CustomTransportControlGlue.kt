package com.guru.androidtv.player

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import androidx.leanback.media.PlaybackTransportControlGlue
import androidx.leanback.widget.Action
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.PlaybackControlsRow
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.guru.androidtv.model.DetailResponse
import com.guru.androidtv.utils.Common
import com.guru.androidtv.utils.Constants.SEEK_BACKWARD_INTERVAL
import com.guru.androidtv.utils.Constants.SEEK_FORWARD_INTERVAL

class CustomTransportControlGlue(context: Context, playerAdapter: BasicMediaPlayerAdapter) :
    PlaybackTransportControlGlue<BasicMediaPlayerAdapter>(context, playerAdapter) {

    private val forwardAction = PlaybackControlsRow.FastForwardAction(context)
    private val rewindAction = PlaybackControlsRow.RewindAction(context)
    private val nextAction = PlaybackControlsRow.SkipNextAction(context)
    private val previousAction = PlaybackControlsRow.SkipPreviousAction(context)

    init {
        isSeekEnabled = true
    }

    override fun onCreatePrimaryActions(primaryActionsAdapter: ArrayObjectAdapter) {
        primaryActionsAdapter.apply {
            add(previousAction)
            add(rewindAction)
            super.onCreatePrimaryActions(this)
            add(forwardAction)
            add(nextAction)
        }
    }


    override fun onActionClicked(action: Action) {
        when (action) {
            forwardAction -> playerAdapter.fastForward()
            rewindAction -> playerAdapter.rewind()
            else -> super.onActionClicked(action)
        }
        onUpdateProgress()
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        host?.let { host ->
            if (host.isControlsOverlayVisible || (event?.repeatCount ?: 0) > 0) {
                return super.onKey(v, keyCode, event)
            }
            return when (keyCode) {
                KeyEvent.KEYCODE_DPAD_RIGHT -> {
                    if (event?.action != KeyEvent.ACTION_DOWN) false else {
                        onActionClicked(forwardAction)
                        true
                    }
                }

                KeyEvent.KEYCODE_DPAD_LEFT -> {
                    if (event?.action != KeyEvent.ACTION_DOWN) false else {
                        onActionClicked(rewindAction)
                        true
                    }
                }

                else -> super.onKey(v, keyCode, event)
            }
        } ?: return super.onKey(v, keyCode, event)
    }

    fun loadMovieInfo(detailResponse: DetailResponse?) {
        title = detailResponse?.original_title
        subtitle = detailResponse?.let { Common.getSubtitle(it) }

        val path = "https://www.themoviedb.org/t/p/w780" + (detailResponse?.backdrop_path ?: "")
        Glide.with(context)
            .asBitmap()
            .load(path)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    controlsRow?.setImageBitmap(context, resource)
                    host?.notifyPlaybackRowChanged()
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    controlsRow?.setImageBitmap(context, null)
                    host?.notifyPlaybackRowChanged()
                }
            })

        playWhenPrepared()
    }
}
