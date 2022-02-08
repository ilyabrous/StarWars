package com.example.starwarsapi.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.starwarsapi.data.local.room.entities.StarshipDbEntity

@Dao
interface StarshipDao {

    @Insert(entity = StarshipDbEntity::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun createStarship(starship: StarshipDbEntity)

    @Insert(entity = StarshipDbEntity::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun createStarship(starships: List<StarshipDbEntity>)

    @Query("SELECT id FROM starships WHERE starship_name = :name")
    suspend fun getIdByName(name: String) : Long
}