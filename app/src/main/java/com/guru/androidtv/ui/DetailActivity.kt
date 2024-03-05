package com.guru.androidtv.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.guru.androidtv.AndroidTvApp
import com.guru.androidtv.R
import com.guru.androidtv.api.Response
import com.guru.androidtv.databinding.ActivityDetailBinding
import com.guru.androidtv.model.DetailResponse
import com.guru.androidtv.player.PlaybackActivity
import com.guru.androidtv.utils.Common
import com.guru.androidtv.utils.Common.Companion.isEllipsized
import com.guru.androidtv.viewmodel.DetailViewModel
import com.guru.androidtv.viewmodel.DetailViewModelFactory

class DetailActivity : FragmentActivity() {
    private lateinit var binding: ActivityDetailBinding
    lateinit var viewModel: DetailViewModel
    val castFragment = ListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        addFragment(castFragment)
        val movieId = intent.getIntExtra("id", 0)

        val repository = (application as AndroidTvApp).tmDbRepo
        viewModel = ViewModelProvider(
            this,
            DetailViewModelFactory(repository, movieId)
        ).get(DetailViewModel::class.java)

        viewModel.movieDetail.observe(this) {
            Log.d("Details_Activity", "${it.data}")
            when (it) {
                is Response.Loading -> {

                }

                is Response.Success -> {

                    setData(it.data)
                }

                is Response.Error -> {

                }
            }
        }

        viewModel.castDetails.observe(this) {
            when (it) {
                is Response.Loading -> {

                }

                is Response.Success -> {
                    if (!it.data?.cast.isNullOrEmpty()) {
                        castFragment.bindCastData(it.data?.cast!!)
                    }
                }

                is Response.Error -> {

                }
            }
        }


        binding.addToMylist.setOnKeyListener { view, keyCode, keyEvent ->
            when (keyCode) {
                KeyEvent.KEYCODE_DPAD_DOWN -> {
                    if (keyEvent.action == KeyEvent.ACTION_DOWN) {
                        castFragment.requestFocus()
                    }
                }
            }

            false
        }

        binding.play.setOnClickListener {
            val intent = Intent(this, PlaybackActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addFragment(castFragment: ListFragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.cast_fragment, castFragment)
        transaction.commit()
    }

    private fun setData(response: DetailResponse?) {
        binding.title.text = response?.title ?: ""
        binding.subtitle.text = response?.let { response -> getSubtitle(response) }
        binding.description.text = response?.overview

        val path = "https://www.themoviedb.org/t/p/w780" + (response?.backdrop_path ?: "")
        Glide.with(this)
            .load(path)
            .into(binding.imgBanner)

        binding.description.isEllipsized { isEllipsized ->
            binding.showMore.visibility = if (isEllipsized) View.VISIBLE else View.GONE

            binding.showMore.setOnClickListener {
                response?.let { it1 -> getSubtitle(it1) }?.let { it2 ->
                    Common.descriptionDialog(
                        this,
                        response.title,
                        it2,
                        response.overview.toString()
                    )
                }
            }

        }
    }

    fun getSubtitle(response: DetailResponse): String {
        val rating = if (response.adult) {
            "18+"
        } else {
            "13+"
        }
        val genres = response.genres.joinToString(
            prefix = " ",
            postfix = " • ",
            separator = " • "
        ) { it.name }

        val hours: Int = response.runtime / 60
        val min: Int = response.runtime % 60

        return rating + genres + hours + "h " + min + "m"
    }
}