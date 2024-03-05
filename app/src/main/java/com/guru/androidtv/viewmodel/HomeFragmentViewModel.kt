package com.guru.androidtv.viewmodel

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

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.getApiService().getMovies(API_KEY)
                _dataList.value = response
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}