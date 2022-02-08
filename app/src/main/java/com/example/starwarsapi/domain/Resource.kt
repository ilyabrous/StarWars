package com.example.starwarsapi.domain

import android.content.SharedPreferences

sealed class Resource<T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val message : Int, val data: T? = null) : Resource<T>()
    class Loading<T>: Resource<T>()
}


