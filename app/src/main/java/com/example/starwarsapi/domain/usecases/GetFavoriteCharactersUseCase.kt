package com.example.starwarsapi.domain.usecases

import com.example.starwarsapi.R
import com.example.starwarsapi.domain.Resource
import com.example.starwarsapi.domain.models.CharacterFavorite
import com.example.starwarsapi.domain.repositories.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetFavoriteCharactersUseCase @Inject constructor (
    private val characterRepository: CharacterRepository,
) {
    operator fun invoke() : Flow<Resource<List<CharacterFavorite>>> = flow {
        try{
            emit(Resource.Loading())
            //val characters = characterRepository.getLocalAllFavoriteCharacters()
            characterRepository.getLocalAllFavoriteCharacters().collect() { list ->
               emit(Resource.Success(list))
            }
        } catch (e: Exception) {
            emit(Resource.Error(R.string.internet_error))
        }

    }
}