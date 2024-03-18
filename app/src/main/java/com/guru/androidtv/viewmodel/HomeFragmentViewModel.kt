package com.guru.androidtv.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guru.androidtv.utils.Constants.API_KEY
import com.guru.androidtv.api.RetrofitInstance
import com.guru.androidtv.model.DataModel
import kotlinx.coroutines.launch

class HomeFragmentViewModel : ViewModel() {
    private val _dataList = MutableLiveData<DataModel>()
    val dataList: LiveData<DataModel> get() = _dataList

    private val _topRatedList = MutableLiveData<DataModel>()
    val topRatedList: LiveData<DataModel> get() = _topRatedList

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                // Fetch popular movies
                val popularResponse = RetrofitInstance.getApiService().getMovies(API_KEY)
                _dataList.value = popularResponse

                // Fetch top-rated movies
                val topRatedResponse = RetrofitInstance.getApiService().getTopRatedMovies(API_KEY)
                _topRatedList.value = topRatedResponse
            } catch (e: Exception) {
                // Handle error
                Log.e("HomeFragmentViewModel", "Error fetching data: ${e.message}")
            }
        }
    }
}
