package com.example.starwarsapi.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.starwarsapi.data.local.room.entities.SpeciesDbEntity

@Dao
interface SpeciesDao {

    @Insert(entity = SpeciesDbEntity::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun createSpecies(species: SpeciesDbEntity)

    @Insert(entity = SpeciesDbEntity::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun createSpecies(species: List<SpeciesDbEntity>)

    @Query("SELECT id FROM species WHERE species_name = :name")
    suspend fun getIdByName(name: String) : Long
}