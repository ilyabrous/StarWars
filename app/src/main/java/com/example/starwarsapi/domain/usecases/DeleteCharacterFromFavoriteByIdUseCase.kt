package com.example.starwarsapi.domain.usecases

import com.example.starwarsapi.R
import com.example.starwarsapi.domain.Resource
import com.example.starwarsapi.domain.models.Character
import com.example.starwarsapi.domain.models.CharacterId
import com.example.starwarsapi.domain.repositories.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class DeleteCharacterFromFavoriteByIdUseCase @Inject constructor (
    private val characterRepository: CharacterRepository,
) {
    suspend operator fun invoke(characterId: CharacterId) : Resource<Boolean> {
        return try{
            characterRepository.deleteCharacterFromFavoriteById(characterId = CharacterId(characterId.id))
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(R.string.delete_error)
        }

    }
}