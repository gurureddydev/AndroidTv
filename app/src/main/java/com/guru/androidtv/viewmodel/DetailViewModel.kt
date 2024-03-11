package com.guru.androidtv.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guru.androidtv.api.Response
import com.guru.androidtv.api.TmDbRepo
import com.guru.androidtv.model.CastResponse
import com.guru.androidtv.model.DetailResponse
import com.guru.androidtv.model.VideoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(val repo: TmDbRepo, id: Int) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getMovieDetails(id)
            repo.getMovieCast(id)
            repo.getMovieVideos(id)
        }
    }

    val movieDetail: LiveData<Response<DetailResponse>>
        get() = repo.movieDetail

    val castDetails: LiveData<Response<CastResponse>>
        get() = repo.castDetail

    val movieVideos: LiveData<Response<VideoResponse>>
        get() = repo.movieVideos
}