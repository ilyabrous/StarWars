package com.example.starwarsapi.presentation.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import com.example.starwarsapi.data.remote.retrofit.CharacterApi
import com.example.starwarsapi.data.repositories.CharacterRepositoryImpl
import com.example.starwarsapi.data.local.LocalDataStorage
import com.example.starwarsapi.data.local.room.AppDatabase
import com.example.starwarsapi.data.local.room.RoomLocalDataStorage
import com.example.starwarsapi.domain.repositories.CharacterRepository
import com.example.starwarsapi.presentation.utils.ConnectivityInterceptor
import com.example.starwarsapi.presentation.utils.WifiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideRetrofit(connInterceptor: ConnectivityInterceptor) : Retrofit {
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(connInterceptor)
                .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://swapi.dev/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }



    @Provides
    @Singleton
    fun provideCharacterApi(retrofit: Retrofit) : CharacterApi {
        return retrofit.create(CharacterApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "starwars.db")
            .build()
    }

    @Provides
    @Singleton
    fun provideRoomStorage(db: AppDatabase) : LocalDataStorage {
        return RoomLocalDataStorage(
            characterDao = db.getCharacterDao(),
            filmDao = db.getFilmDao(),
            speciesDao = db.getSpeciesDao(),
            starshipDao = db.getStarshipDao(),
            vehicleDao = db.getVehicleDao(),
        )
    }

    @Provides
    @Singleton
    fun provideDispatcher() : CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    @Singleton
    fun provideCharacterRepository(localDataStorage: LocalDataStorage, api: CharacterApi, dispatcher: CoroutineDispatcher) : CharacterRepository{
        return CharacterRepositoryImpl(localDataStorage, api, dispatcher)
    }

    @Provides
    @Singleton
    fun provideWifiService(@ApplicationContext context: Context) : WifiService {
        return WifiService(context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
    }

    @Provides
    @Singleton
    fun provideConnectivityInterceptor(wifiService: WifiService) : ConnectivityInterceptor {
        return ConnectivityInterceptor(wifiService)
    }

}