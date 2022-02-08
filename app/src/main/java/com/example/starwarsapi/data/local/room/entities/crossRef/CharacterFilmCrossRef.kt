package com.example.starwarsapi.data.local.room.entities.crossRef

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.starwarsapi.data.local.room.entities.CharacterDbEntity
import com.example.starwarsapi.data.local.room.entities.FilmDbEntity


@Entity(
    tableName = "character_film_cross_ref",
    primaryKeys = ["character_id", "film_id"],
    foreignKeys = [
        ForeignKey(
            entity = CharacterDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["character_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = FilmDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["film_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE)
    ],
)
data class CharacterFilmCrossRef(
    @ColumnInfo(name = "character_id") val characterId: Long,
    @ColumnInfo(name = "film_id") val filmId: Long,
)