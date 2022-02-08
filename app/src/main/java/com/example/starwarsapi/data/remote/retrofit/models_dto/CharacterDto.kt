package com.example.starwarsapi.data.remote.retrofit.models_dto


import com.google.gson.annotations.SerializedName

data class CharacterDto(
    @SerializedName("birth_year")
    val birthYear: String,
    @SerializedName("created")
    val created: String,
    @SerializedName("edited")
    val edited: String,
    @SerializedName("eye_color")
    val eyeColor: String,
    @SerializedName("films")
    val filmsUrls: List<String>,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("hair_color")
    val hairColor: String,
    @SerializedName("height")
    val height: String,
    @SerializedName("homeworld")
    val homeWorld: String,
    @SerializedName("mass")
    val mass: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("skin_color")
    val skinColor: String,
    @SerializedName("species")
    val speciesUrls: List<String>,
    @SerializedName("starships")
    val starshipsUrls: List<String>,
    @SerializedName("url")
    val url: String,
    @SerializedName("vehicles")
    val vehiclesUrls: List<String>
)