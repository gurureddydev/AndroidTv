package com.guru.androidtv.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.guru.androidtv.model.CastResponse
import com.guru.androidtv.model.DetailResponse
import com.guru.androidtv.model.VideoResponse
import com.guru.androidtv.utils.Constants.API_KEY

class TmDbRepo(private val service: ApiService) {

    private val detailData = MutableLiveData<Response<DetailResponse>>()
    private val castData = MutableLiveData<Response<CastResponse>>()
    private val videoData = MutableLiveData<Response<VideoResponse>>()
    val movieVideos: LiveData<Response<VideoResponse>>
        get() = videoData

    val movieDetail: LiveData<Response<DetailResponse>>
        get() = detailData

    val castDetail: LiveData<Response<CastResponse>>
        get() = castData

    suspend fun getMovieDetails(id: Int) {
        try {
            val result = service.getMovieDetails(id, API_KEY)

            if (result.isSuccessful) {
                detailData.postValue(Response.Success(result.body()))
                Log.d("TmDbRepo", "Movie details retrieved successfully")
            } else {
                detailData.postValue(Response.Error(result.errorBody().toString()))
                Log.e("TmDbRepo", "Error getting movie details: ${result.message()}")
            }
        } catch (e: Exception) {
            detailData.postValue(Response.Error(e.message.toString()))
            Log.e("TmDbRepo", "Exception getting movie details: ${e.message}")
        }
    }

    suspend fun getMovieCast(id: Int) {
        try {
            val result = service.getMovieCast(id, API_KEY)

            if (result.isSuccessful) {
                castData.postValue(Response.Success(result.body()))
                Log.d("TmDbRepo", "Cast details retrieved successfully")
            } else {
                castData.postValue(Response.Error(result.errorBody().toString()))
                Log.e("TmDbRepo", "Error getting cast details: ${result.message()}")
            }
        } catch (e: Exception) {
            castData.postValue(Response.Error(e.message.toString()))
            Log.e("TmDbRepo", "Exception getting cast details: ${e.message}")
        }
    }

    suspend fun getMovieVideos(id: Int) {
        try {
            val result = service.getMovieVideos(id, API_KEY)

            if (result.isSuccessful) {
                videoData.postValue(Response.Success(result.body()))
                Log.d("TmDbRepo", "Movie videos retrieved successfully")
            } else {
                videoData.postValue(Response.Error(result.errorBody().toString()))
                Log.e("TmDbRepo", "Error getting movie videos: ${result.message()}")
            }
        } catch (e: Exception) {
            videoData.postValue(Response.Error(e.message.toString()))
            Log.e("TmDbRepo", "Exception getting movie videos: ${e.message}")
        }
    }
}
