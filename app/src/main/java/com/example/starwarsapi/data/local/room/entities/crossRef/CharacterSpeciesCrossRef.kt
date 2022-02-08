package com.example.starwarsapi.data.local.room.entities.crossRef

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.starwarsapi.data.local.room.entities.CharacterDbEntity
import com.example.starwarsapi.data.local.room.entities.SpeciesDbEntity

@Entity(
    tableName = "character_species_cross_ref",
    foreignKeys = [
        ForeignKey(
            entity = CharacterDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["character_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = SpeciesDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["species_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE)
    ],
    primaryKeys = ["character_id", "species_id"],
)
data class CharacterSpeciesCrossRef(
    @ColumnInfo(name = "character_id") val characterId: Long,
    @ColumnInfo(name = "species_id") val speciesId: Long,
)