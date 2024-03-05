package com.guru.androidtv.api

import retrofit2.Response

sealed class Response<T>(val data: T? = null, val error: String? = null) {
    class Loading<T> : com.guru.androidtv.api.Response<T>()
    class Success<T>(data: T? = null) : com.guru.androidtv.api.Response<T>(data = data)
    class Error<T>(error: String?) : com.guru.androidtv.api.Response<T>(error = error)
}