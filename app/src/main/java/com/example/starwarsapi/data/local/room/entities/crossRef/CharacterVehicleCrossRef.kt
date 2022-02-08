package com.example.starwarsapi.data.local.room.entities.crossRef

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.starwarsapi.data.local.room.entities.CharacterDbEntity
import com.example.starwarsapi.data.local.room.entities.VehicleDbEntity

@Entity(
    tableName = "character_vehicle_cross_ref",
    primaryKeys = ["character_id", "vehicle_id"],
    foreignKeys = [
        ForeignKey(
            entity = CharacterDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["character_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = VehicleDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["vehicle_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE)
    ]
)
data class CharacterVehicleCrossRef(
    @ColumnInfo(name = "character_id") val characterId: Long,
    @ColumnInfo(name = "vehicle_id") val vehicleId: Long,
)