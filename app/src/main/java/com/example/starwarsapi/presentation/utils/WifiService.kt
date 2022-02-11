package com.example.starwarsapi.presentation.utils

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.example.starwarsapi.domain.NetworkException
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class WifiService(
    private val connectivityManager: ConnectivityManager
) {

    fun isNetworkAvailable(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }

}

class ConnectivityInterceptor(private val wifiService: WifiService): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!wifiService.isNetworkAvailable()) {
            throw NetworkException()
        } else {
            return chain.proceed(chain.request())
        }
    }
}

