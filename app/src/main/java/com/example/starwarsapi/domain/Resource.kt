package com.example.starwarsapi.domain

import android.content.SharedPreferences

//todo i think need to create more sealed class because i dont use loading in some cases...
sealed class Resource<T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val message : Int, val data: T? = null) : Resource<T>()
    class Loading<T>: Resource<T>()
}


