package com.guru.androidtv.player

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.guru.androidtv.R
import com.guru.androidtv.databinding.ActivityPlaybackBinding

class PlaybackActivity : FragmentActivity() {
    private lateinit var binding: ActivityPlaybackBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_playback)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_view, VideoFragment())
                .commit()
        }
    }
}
