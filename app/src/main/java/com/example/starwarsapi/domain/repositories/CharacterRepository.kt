package com.example.starwarsapi.domain.repositories

import com.example.starwarsapi.domain.models.Character
import com.example.starwarsapi.domain.models.CharacterFavorite
import com.example.starwarsapi.domain.models.CharacterId
import com.example.starwarsapi.domain.models.CharacterName
import com.example.starwarsapi.domain.models.CharacterNameParam
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    /**
     * For getting remote characters name without save. Only list. Search screen
     */
    suspend fun getRemoteCharactersByName(characterNameParam: CharacterNameParam) : List<CharacterName>

    /**
     * For getting local favorite characters name. Only list. Favorite screen
     */
    suspend fun getLocalAllFavoriteCharacters() : Flow<List<CharacterFavorite>>

    /**
     * For getting local favorite characters detail. Character screen
     */
    suspend fun getLocalCharacterDetailById(characterId: CharacterId) : Character

    /**
     * For getting remote favorite characters detail. Character screen
     */
    suspend fun getRemoteCharacterDetailById(characterId: CharacterId) : Character

    /**
     * For adding favorite characters detail. Character screen
     */
    suspend fun addCharacterToFavorite(character: Character)

    /**
     * For deleting character. Character screen
     */
    suspend fun deleteCharacterFromFavoriteById(characterId: CharacterId)

    /**
     * For checking is character saved?. Character screen
     */
    suspend fun isCharacterSaved(characterId: CharacterId) : Flow<Boolean>

}