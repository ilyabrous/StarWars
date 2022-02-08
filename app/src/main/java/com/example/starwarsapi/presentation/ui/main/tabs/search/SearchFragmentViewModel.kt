package com.example.starwarsapi.presentation.ui.main.tabs.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsapi.presentation.utils.MutableLiveEvent
import com.example.starwarsapi.presentation.utils.publishEvent
import com.example.starwarsapi.presentation.utils.share
import com.example.starwarsapi.R
import com.example.starwarsapi.domain.Resource
import com.example.starwarsapi.domain.models.CharacterId
import com.example.starwarsapi.domain.models.CharacterNameParam
import com.example.starwarsapi.domain.usecases.AddCharacterToFavoriteUseCase
import com.example.starwarsapi.domain.usecases.DeleteCharacterFromFavoriteByIdUseCase
import com.example.starwarsapi.domain.usecases.GetCharacterDetailByIdUseCase
import com.example.starwarsapi.domain.usecases.GetCharactersByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(
    private val getCharactersByNameUseCase: GetCharactersByNameUseCase,
    private val addCharacterToFavoriteUseCase: AddCharacterToFavoriteUseCase,
    private val deleteCharacterFromFavoriteByIdUseCase: DeleteCharacterFromFavoriteByIdUseCase,
    private val getCharacterDetailByIdUseCase: GetCharacterDetailByIdUseCase
) : ViewModel(), SearchAdapter.Listener {

    private var _uiStateLiveData = MutableLiveData<SearchUiState>()
    var uiStateLiveData : LiveData<SearchUiState> = _uiStateLiveData

    private val _showErrorEvent = MutableLiveEvent<Int>()
    val showErrorEvent = _showErrorEvent.share()

    private val _openCharacter = MutableLiveEvent<Long>()
    val openCharacter = _openCharacter.share()

    private val userIdsInProgress = mutableSetOf<Long>()

    private var flow : Job? = null
    private val handlerException = CoroutineExceptionHandler { _, _ ->
        showError(R.string.coroutines_error)
    }

    fun findCharacters(characterNameParam: CharacterNameParam)  {
        flow?.cancel()

        flow = getCharactersByNameUseCase(characterNameParam).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _uiStateLiveData.value = SearchUiState(isLoading = false, characterItems = result.data)
                }
                is Resource.Error -> {
                    _uiStateLiveData.value = SearchUiState(isLoading = false)
                    showError(result.message)
                }
                is Resource.Loading -> {
                    _uiStateLiveData.value = SearchUiState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)

    }

    /*
    todo in my uiState i change val isLoading to var? is it good? or better would create a new full state?
    or i would separate my uiState?
    */
    override fun saveCharacter(id: Long) {

        //change uiState for showing progressBar for changing character
        showProgressItem(id, true)

        //start getting a full detail about character
        viewModelScope.launch(handlerException) {
            val resultCharacter = getCharacterDetailByIdUseCase(
                characterId = CharacterId(
                    id = id
                )
            )

            when(resultCharacter) {
                is Resource.Success -> {
                    val character = resultCharacter.data

                    when(val resultAdding = addCharacterToFavoriteUseCase(character)) {
                        is Resource.Success -> {
                            changeBookmarkedState(id, true)
                        }
                        is Resource.Error -> {
                            showError(resultAdding.message)
                        }
                        else -> {
                            showError(R.string.unexpected_error)
                        }
                    }
                }
                is Resource.Error -> {
                    showError(resultCharacter.message)
                }
                else -> {
                    showError(R.string.save_error)
                }
            }

            showProgressItem(id, false)
        }


    }

    override fun deleteCharacter(id: Long) {
        //change uiState for showing progressBar for changing character
        showProgressItem(id, true)

        //start getting a full detail about character
        viewModelScope.launch(handlerException) {
            val resultDeleting = deleteCharacterFromFavoriteByIdUseCase(
                characterId = CharacterId(
                    id = id
                )
            )

            when(resultDeleting) {
                is Resource.Success -> {
                    changeBookmarkedState(id, false)
                }
                is Resource.Error -> {
                    showError(resultDeleting.message)
                }
                else -> {
                    showError(R.string.save_error)
                }
            }
            showProgressItem(id, false)
        }
    }

    private fun changeBookmarkedState(id: Long, isBookmarked: Boolean) {
        val stateUi = _uiStateLiveData.value

        stateUi?.characterItems?.forEach { item ->
            if (item.id == id) {
                item.isBookmarked = isBookmarked
            }
        }

        _uiStateLiveData.value = stateUi!!
    }

    private fun showProgressItem(id: Long, isInProgress: Boolean) {
        if(isInProgress) {
            userIdsInProgress.add(id)
        } else {
            userIdsInProgress.remove(id)
        }

        val state = _uiStateLiveData.value
        state?.characterItems?.forEach { item ->
            if(item.id == id) {
                item.isInProgress = isInProgress
            }

        }

        _uiStateLiveData.value = state!!
    }

    override fun openCharacter(id: Long) {
        _openCharacter.publishEvent(id)
    }

    private fun showError(idError : Int) {
        _showErrorEvent.publishEvent(idError)
    }

}

data class SearchUiState(
    val isLoading: Boolean = false,
    val characterItems: List<CharacterItemUiState> = listOf(),
)



data class CharacterItemUiState(
    val id: Long,
    val name: String,
    var isBookmarked: Boolean = false,
    var isInProgress: Boolean = false
)