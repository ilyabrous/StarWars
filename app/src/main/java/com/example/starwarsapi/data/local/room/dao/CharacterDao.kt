package com.example.starwarsapi.data.local.room.dao

import androidx.room.*
import com.example.starwarsapi.data.local.room.entities.CharacterAndAllReferencesTuple
import com.example.starwarsapi.data.local.room.entities.CharacterDbEntity
import com.example.starwarsapi.data.local.room.entities.CharacterFavoriteTuple
import com.example.starwarsapi.data.local.room.entities.crossRef.CharacterFilmCrossRef
import com.example.starwarsapi.data.local.room.entities.crossRef.CharacterSpeciesCrossRef
import com.example.starwarsapi.data.local.room.entities.crossRef.CharacterStarshipCrossRef
import com.example.starwarsapi.data.local.room.entities.crossRef.CharacterVehicleCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Transaction
    @Query("SELECT * FROM characters WHERE characters.id = :characterId")
    suspend fun getCharacterById(characterId: Long) : CharacterAndAllReferencesTuple

    @Query("SELECT * FROM characters")
    fun getAllFavoriteCharacters() : Flow<List<CharacterFavoriteTuple>>

    @Query("SELECT id FROM characters WHERE name = :name")
    suspend fun getIdByName(name: String) : Long

    @Insert(entity = CharacterDbEntity::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun createAccount(characterDbEntity: CharacterDbEntity)

    @Query("DELETE FROM characters WHERE id = :id")
    suspend fun deleteAccountById(id: Long)

    @Insert(entity = CharacterFilmCrossRef::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun createCharacterFilmRelation(characterFilmCrossRef: CharacterFilmCrossRef)

    @Insert(entity = CharacterSpeciesCrossRef::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun createCharacterSpeciesRelation(characterSpeciesCrossRef: CharacterSpeciesCrossRef)

    @Insert(entity = CharacterStarshipCrossRef::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun createCharacterStarshipRelation(characterStarshipCrossRef: CharacterStarshipCrossRef)

    @Insert(entity = CharacterVehicleCrossRef::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun createCharacterVehicleRelation(characterVehicleCrossRef: CharacterVehicleCrossRef)

    @Insert(entity = CharacterFilmCrossRef::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun createCharacterFilmRelation(characterFilmCrossRefList: List<CharacterFilmCrossRef>)

    @Insert(entity = CharacterSpeciesCrossRef::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun createCharacterSpeciesRelation(characterSpeciesCrossRefList: List<CharacterSpeciesCrossRef>)

    @Insert(entity = CharacterStarshipCrossRef::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun createCharacterStarshipRelation(characterStarshipCrossRefList: List<CharacterStarshipCrossRef>)

    @Insert(entity = CharacterVehicleCrossRef::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun createCharacterVehicleRelation(characterVehicleCrossRefList: List<CharacterVehicleCrossRef>)

    @Query("SELECT COUNT(id) FROM characters WHERE id = :id")
    fun getCountCharacterById(id: Long) : Flow<Int>



    @Transaction
    suspend fun createFullCharacterRelation(
        characterFilmCrossRef : CharacterFilmCrossRef?,
        characterVehicleCrossRef: CharacterVehicleCrossRef?,
        characterStarshipCrossRef: CharacterStarshipCrossRef?,
        characterSpeciesCrossRef: CharacterSpeciesCrossRef?
    ) {
        if (characterFilmCrossRef != null) createCharacterFilmRelation(characterFilmCrossRef)
        if (characterVehicleCrossRef != null) createCharacterVehicleRelation(characterVehicleCrossRef)
        if (characterStarshipCrossRef != null) createCharacterStarshipRelation(characterStarshipCrossRef)
        if (characterSpeciesCrossRef != null) createCharacterSpeciesRelation(characterSpeciesCrossRef)

    }

    @Transaction
    suspend fun createFullCharacterRelation(
        characterFilmCrossRef : List<CharacterFilmCrossRef>?,
        characterVehicleCrossRef: List<CharacterVehicleCrossRef>?,
        characterStarshipCrossRef: List<CharacterStarshipCrossRef>?,
        characterSpeciesCrossRef: List<CharacterSpeciesCrossRef>?
    ) {
        if (!characterFilmCrossRef.isNullOrEmpty()) createCharacterFilmRelation(characterFilmCrossRef)
        if (!characterVehicleCrossRef.isNullOrEmpty()) createCharacterVehicleRelation(characterVehicleCrossRef)
        if (!characterStarshipCrossRef.isNullOrEmpty()) createCharacterStarshipRelation(characterStarshipCrossRef)
        if (!characterSpeciesCrossRef.isNullOrEmpty()) createCharacterSpeciesRelation(characterSpeciesCrossRef)

    }
}