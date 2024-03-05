package com.guru.androidtv.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.guru.androidtv.api.TmDbRepo

class DetailViewModelFactory(val repo: TmDbRepo, val movieId: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(repo, movieId) as T
    }
}