package com.example.starwarsapi.data.local.room.entities.crossRef

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.starwarsapi.data.local.room.entities.CharacterDbEntity
import com.example.starwarsapi.data.local.room.entities.StarshipDbEntity

@Entity(
    tableName = "character_starship_cross_ref",
    foreignKeys = [
        ForeignKey(
            entity = CharacterDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["character_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = StarshipDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["starship_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE)
    ],
    primaryKeys = ["character_id", "starship_id"],
)
data class CharacterStarshipCrossRef(
    @ColumnInfo(name = "character_id") val characterId: Long,
    @ColumnInfo(name = "starship_id") val starshipId: Long,
)