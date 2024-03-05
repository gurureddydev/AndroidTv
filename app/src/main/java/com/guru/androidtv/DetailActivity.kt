package com.guru.androidtv

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.guru.androidtv.api.Response
import com.guru.androidtv.databinding.ActivityDetailBinding
import com.guru.androidtv.model.DetailResponse
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
                    if (it.data?.cast?.isNotEmpty() == true) {
                        it.data.cast.let { it1 -> castFragment.bindCastData(it1) }
                    }
                }

                is Response.Error -> {

                }
            }
        }
    }

    private fun addFragment(castFragment: ListFragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.cast_fragment, castFragment)
        transaction.commit()
    }

    private fun setData(it: DetailResponse?) {
        binding.title.text = it?.title ?: ""
        binding.subTitle.text = it?.title ?: ""
        binding.description.text = it?.let { it1 -> getSubtitle(it1) }
    }

    fun getSubtitle(response: DetailResponse): String {
        val rating = if (response.adult) {
            "18+"
        } else {
            "13+"
        }
        val genres = response.genres.joinToString(
            prefix = " ",
            postfix = " . ",
            separator = "."
        ) { it.name }

        val hours: Int = response.runtime / 60
        val min: Int = response.runtime % 60

        return rating + genres + hours + "h " + min + "min"
    }
}