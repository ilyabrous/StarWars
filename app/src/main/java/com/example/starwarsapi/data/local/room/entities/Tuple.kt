package com.example.starwarsapi.data.local.room.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.starwarsapi.data.local.room.entities.crossRef.CharacterFilmCrossRef
import com.example.starwarsapi.data.local.room.entities.crossRef.CharacterSpeciesCrossRef
import com.example.starwarsapi.data.local.room.entities.crossRef.CharacterStarshipCrossRef
import com.example.starwarsapi.data.local.room.entities.crossRef.CharacterVehicleCrossRef

data class CharacterFavoriteTuple(
    @ColumnInfo(name = "id" ) val id: Long,
    @ColumnInfo(name = "name" ) val name: String
)

data class CharacterAndAllReferencesTuple(
    @Embedded val characterDbEntity: CharacterDbEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = CharacterFilmCrossRef::class,
            parentColumn = "character_id",
            entityColumn = "film_id"
        )
    )
    val films: List<FilmDbEntity>?,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = CharacterSpeciesCrossRef::class,
            parentColumn = "character_id",
            entityColumn = "species_id"
        )
    )
    val species: List<SpeciesDbEntity>?,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = CharacterStarshipCrossRef::class,
            parentColumn = "character_id",
            entityColumn = "starship_id"
        )
    )
    val starships: List<StarshipDbEntity>?,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = CharacterVehicleCrossRef::class,
            parentColumn = "character_id",
            entityColumn = "vehicle_id"
        )
    )
    val vehicles: List<VehicleDbEntity>?,
)