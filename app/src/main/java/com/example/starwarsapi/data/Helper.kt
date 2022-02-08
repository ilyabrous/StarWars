package com.example.starwarsapi.data

class DataHelper {

    companion object {
        fun getIdFromUrl(url: String) : Long{
            val result = url.filter { it.isDigit() }
            return result.toLong()
        }
    }
}