package com.example.starwarsapi.data.local

import com.example.starwarsapi.data.local.room.entities.CharacterAndAllReferencesTuple
import com.example.starwarsapi.data.local.room.entities.CharacterFavoriteTuple
import com.example.starwarsapi.domain.models.Character
import kotlinx.coroutines.flow.Flow

interface LocalDataStorage {

    suspend fun saveLocalCharacter(character: Character)

    suspend fun getLocalAllFavoriteCharacters(): Flow<List<CharacterFavoriteTuple>>

    suspend fun getLocalCharactersDetailById(id: Long): CharacterAndAllReferencesTuple

    suspend fun deleteFavoriteCharacterById(id: Long)

    suspend fun isSavedCharacter(id: Long) : Flow<Boolean>

}