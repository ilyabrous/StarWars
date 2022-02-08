package com.example.starwarsapi.data.remote.retrofit

import com.example.starwarsapi.data.remote.retrofit.models_dto.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterApi {

    //todo: should i replace string by characterNameParam, but how can i set characterNameParam.name to value: search?
    @GET("people")
    suspend fun getCharactersByName(@Query("search") name: String) : CharacterResponseDto

    @GET("people/{characterId}")
    suspend fun getCharacterById(@Path("characterId") characterId: String) : CharacterDto

    @GET("films/{filmId}")
    suspend fun getFilmById(@Path("filmId") filmId: String) : FilmDto

    @GET("starships/{starshipId}")
    suspend fun getStarshipById(@Path("starshipId") starshipId: String) : StarshipDto

    @GET("vehicles/{vehicleId}")
    suspend fun getVehicleById(@Path("vehicleId") vehicleId: String) : VehicleDto

    @GET("planets/{homeWorldId}")
    suspend fun getHomeWorldById(@Path("homeWorldId") homeWorldId: String) : HomeworldDto

    @GET("species/{speciesId}")
    suspend fun getSpeciesById(@Path("speciesId") speciesId: String) : SpeciesDto
}