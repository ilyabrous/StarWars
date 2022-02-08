package com.example.starwarsapi.data.remote.retrofit.models_dto


import com.google.gson.annotations.SerializedName

data class CharacterResponseDto(
//    @SerializedName("count")
//    val count: Int,
//    @SerializedName("next")
//    val next: Any,
//    @SerializedName("previous")
//    val previous: Any,
    @SerializedName("results")
    val CharacterDto: List<CharacterDto>
)