package com.example.starwarsapi.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.starwarsapi.data.local.room.entities.VehicleDbEntity

@Dao
interface VehicleDao {

    @Insert(entity = VehicleDbEntity::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun createVehicle(vehicle: VehicleDbEntity)

    @Insert(entity = VehicleDbEntity::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun createVehicle(vehicles: List<VehicleDbEntity>)

    @Query("SELECT id FROM vehicles WHERE vehicles_name = :name")
    suspend fun getIdByName(name: String) : Long
}