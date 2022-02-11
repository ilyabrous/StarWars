package com.example.starwarsapi.domain.usecases

import com.example.starwarsapi.R
import com.example.starwarsapi.domain.NetworkException
import com.example.starwarsapi.domain.Resource
import com.example.starwarsapi.domain.models.CharacterId
import com.example.starwarsapi.domain.models.CharacterNameParam
import com.example.starwarsapi.domain.repositories.CharacterRepository
import com.example.starwarsapi.presentation.ui.main.tabs.search.CharacterItemUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

import java.lang.Exception
import javax.inject.Inject


//todo i think it is stupid mapping in domain data class to uiState..... because domain is isolated layer..
class GetCharactersByNameUseCase@Inject constructor (
    private val characterRepository: CharacterRepository,
) {
    operator fun invoke(characterNameParam: CharacterNameParam) : Flow<Resource<List<CharacterItemUiState>>> = flow {
        try{
            emit(Resource.Loading())
            val characters = characterRepository.getRemoteCharactersByName(characterNameParam)
            val savedCharacters = characterRepository
                .getLocalAllFavoriteCharacters()
                .map {  list ->
                    list.map {
                        it.id
                    }
                }
            savedCharacters.collect{listIdSaved ->
                val listCharacterUiState = mutableListOf<CharacterItemUiState>()
                characters.forEach { character ->

                    listCharacterUiState.add(
                        CharacterItemUiState(
                            id = character.id,
                            name = character.name,
                            isBookmarked = listIdSaved.contains(character.id)
                        ))

                    emit(Resource.Success(listCharacterUiState))
                }
            }


        }
        catch (e: NetworkException) {
            emit(Resource.Error(R.string.internet_error))
        }
        catch (e: Exception) {
            emit( Resource.Error(R.string.unexpected_error))
        }

    }
}


