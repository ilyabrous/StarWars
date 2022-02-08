package com.example.starwarsapi.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.starwarsapi.data.local.room.entities.FilmDbEntity

@Dao
interface FilmDao {

    @Insert(entity = FilmDbEntity::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun createFilm(films: FilmDbEntity)

    @Insert(entity = FilmDbEntity::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun createFilm(films: List<FilmDbEntity>)

    @Query("SELECT id FROM films WHERE film_title = :title")
    suspend fun getIdByName(title: String) : Long
}