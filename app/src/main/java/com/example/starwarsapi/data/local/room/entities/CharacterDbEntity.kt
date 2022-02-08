package com.example.starwarsapi.data.local.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "characters",
    indices = [
        Index("name", unique = true)
    ]
)
data class CharacterDbEntity (
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "birth_year" ) val birthYear: String,
    @ColumnInfo(name = "created_at" ) val created: String,
    @ColumnInfo(name = "edited_at" ) val edited: String,
    @ColumnInfo(name = "eye_color" ) val eyeColor: String,
    @ColumnInfo(name = "gender" ) val gender: String,
    @ColumnInfo(name = "hair_color" ) val hairColor: String,
    @ColumnInfo(name = "height" ) val height: String,
    @ColumnInfo(name = "home_world" ) val homeWorld: String,
    @ColumnInfo(name = "mass" ) val mass: String,
    @ColumnInfo(name = "name" ) val name: String,
    @ColumnInfo(name = "skin_color" ) val skinColor: String,
    @ColumnInfo(name = "url" ) val url: String,
)

