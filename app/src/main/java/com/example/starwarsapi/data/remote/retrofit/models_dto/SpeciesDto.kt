package com.example.starwarsapi.data.remote.retrofit.models_dto


import com.google.gson.annotations.SerializedName

data class SpeciesDto(
    @SerializedName("average_height")
    val averageHeight: String,
    @SerializedName("average_lifespan")
    val averageLifespan: String,
    @SerializedName("classification")
    val classification: String,
    @SerializedName("created")
    val created: String,
    @SerializedName("designation")
    val designation: String,
    @SerializedName("edited")
    val edited: String,
    @SerializedName("eye_colors")
    val eyeColors: String,
    @SerializedName("films")
    val films: List<String>,
    @SerializedName("hair_colors")
    val hairColors: String,
    @SerializedName("homeworld")
    val homeWorld: String,
    @SerializedName("language")
    val language: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("people")
    val people: List<String>,
    @SerializedName("skin_colors")
    val skinColors: String,
    @SerializedName("url")
    val url: String
)