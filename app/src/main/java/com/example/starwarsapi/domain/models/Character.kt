package com.example.starwarsapi.domain.models

data class Character(
    val id: Long,
    val birthYear: String,
    val created: String,
    val edited: String,
    val eyeColor: String,
    val films: List<Film>?,
    val gender: String,
    val hairColor: String,
    val height: String,
    val homeWorld: String,
    val mass: String,
    val name: String,
    val skinColor: String,
    val species: List<Species>?,
    val starships: List<Starship>?,
    val url: String,
    val vehicles: List<Vehicle>?
)