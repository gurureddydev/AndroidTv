package com.guru.androidtv.player

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.guru.androidtv.R
import com.guru.androidtv.databinding.ActivityPlaybackBinding
import com.guru.androidtv.model.DetailResponse

class PlaybackActivity : FragmentActivity() {
    private lateinit var binding: ActivityPlaybackBinding

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_playback)

        val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("movie_detail", DetailResponse::class.java)
        } else {
            intent.getParcelableExtra<DetailResponse>("movie_detail")
        }
        val fragment = VideoFragment()
        val bundle = Bundle().apply {
            putParcelable("movie_detail", data)
        }

        fragment.arguments = bundle

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_view, fragment)
                .commit()

        }
    }
}

