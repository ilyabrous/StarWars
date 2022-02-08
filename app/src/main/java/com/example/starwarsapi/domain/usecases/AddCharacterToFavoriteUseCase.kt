package com.example.starwarsapi.domain.usecases

import android.util.Log
import com.example.starwarsapi.R
import com.example.starwarsapi.domain.Resource
import com.example.starwarsapi.domain.models.Character
import com.example.starwarsapi.domain.repositories.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class AddCharacterToFavoriteUseCase @Inject constructor (
    private val characterRepository: CharacterRepository,
    ) {
        suspend operator fun invoke(character: Character) : Resource<Boolean> {
            return try{
                characterRepository.addCharacterToFavorite(character)
                Log.d("AAA","Save character successful")
                Resource.Success(true)

            } catch (e: Exception) {
                Log.d("AAA","Save character error ${e.localizedMessage}")
                Resource.Error(message = R.string.unexpected_error)
            }
        }
    }