package com.example.starwarsapi.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.starwarsapi.data.local.room.dao.*
import com.example.starwarsapi.data.local.room.entities.*
import com.example.starwarsapi.data.local.room.entities.crossRef.CharacterFilmCrossRef
import com.example.starwarsapi.data.local.room.entities.crossRef.CharacterSpeciesCrossRef
import com.example.starwarsapi.data.local.room.entities.crossRef.CharacterStarshipCrossRef
import com.example.starwarsapi.data.local.room.entities.crossRef.CharacterVehicleCrossRef

@Database(
    version = 2,
    entities = [
        CharacterDbEntity::class,
        FilmDbEntity::class,
        SpeciesDbEntity::class,
        StarshipDbEntity::class,
        VehicleDbEntity::class,
        CharacterFilmCrossRef::class,
        CharacterSpeciesCrossRef::class,
        CharacterStarshipCrossRef::class,
        CharacterVehicleCrossRef::class,
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getCharacterDao() : CharacterDao

    abstract fun getFilmDao() : FilmDao

    abstract fun getSpeciesDao() : SpeciesDao

    abstract fun getStarshipDao() : StarshipDao

    abstract fun getVehicleDao() : VehicleDao

}