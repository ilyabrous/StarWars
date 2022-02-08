package com.example.starwarsapi.presentation.ui.main.tabs.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsapi.R
import com.example.starwarsapi.domain.Resource
import com.example.starwarsapi.domain.models.CharacterId
import com.example.starwarsapi.domain.usecases.AddCharacterToFavoriteUseCase
import com.example.starwarsapi.domain.usecases.DeleteCharacterFromFavoriteByIdUseCase
import com.example.starwarsapi.domain.usecases.GetCharacterDetailByIdUseCase
import com.example.starwarsapi.domain.usecases.GetFavoriteCharactersUseCase
import com.example.starwarsapi.presentation.ui.main.tabs.search.SearchAdapter
import com.example.starwarsapi.presentation.utils.MutableLiveEvent
import com.example.starwarsapi.presentation.utils.publishEvent
import com.example.starwarsapi.presentation.utils.share
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoriteCharactersUseCase: GetFavoriteCharactersUseCase,
    private val deleteCharacterFromFavoriteByIdUseCase: DeleteCharacterFromFavoriteByIdUseCase,
    private val getCharacterDetailByIdUseCase: GetCharacterDetailByIdUseCase,
    private val addCharacterToFavoriteUseCase: AddCharacterToFavoriteUseCase,
) : ViewModel(), FavoriteAdapter.Listener  {

    private val _favoriteUiStateLiveDate = MutableLiveData<FavoriteUiState>()
    val favoriteUiStateLiveDate = _favoriteUiStateLiveDate.share()

    private val _favoriteItemsUiStateLiveDate = MutableLiveData<FavoriteItemsUiState>()
    val favoriteItemsUiStateLiveDate = _favoriteItemsUiStateLiveDate.share()

    private val _openCharacter = MutableLiveEvent<Long>()
    val openCharacter = _openCharacter.share()

    private val _showEvent = MutableLiveEvent<Int>()
    val showEvent = _showEvent.share()

    private val _showUndoSnackbar = MutableLiveEvent<Long>()
    val showUndoSnackbar =  _showUndoSnackbar.share()

    private val userIdsInProgress = mutableSetOf<Long>()

    private val handlerException = CoroutineExceptionHandler { _, _ ->
        showToast(R.string.coroutines_error)
    }


    init {
        getFavoriteCharacters()
    }

    fun saveCharacter(id: Long) {

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
                            showToast(R.string.save_successfully)
                        }
                        is Resource.Error -> {
                            showToast(resultAdding.message)
                        }
                        else -> {
                            showToast(R.string.unexpected_error)
                        }
                    }
                }
                is Resource.Error -> {
                    showToast(resultCharacter.message)
                }
                else -> {
                    showToast(R.string.save_error)
                }
            }

            showProgressItem(id, false)
        }


    }

    private fun getFavoriteCharacters() {
        viewModelScope.launch {
            getFavoriteCharactersUseCase().collect { resource ->
                if(resource is Resource.Success) {
                    _favoriteItemsUiStateLiveDate.value = FavoriteItemsUiState(
                        listItem = resource.data.map { character ->
                            FavoriteItemUiState(
                                id = character.id,
                                name = character.name
                            )
                        }
                    )
                }
            }
        }
    }

    override fun deleteCharacter(id: Long) {
        showProgressItem(id, true)
        viewModelScope.launch {
            val resultDeleting = deleteCharacterFromFavoriteByIdUseCase(characterId = CharacterId(id))

            when(resultDeleting) {
                is Resource.Error -> showToast(resultDeleting.message)
                is Resource.Success -> showSnackbar(id)
                else ->  showToast(R.string.unexpected_error)
            }

            showProgressItem(id, false)
        }
    }



    override fun openCharacter(id: Long) {
        _openCharacter.publishEvent(id)
    }

    private fun showToast(idError : Int) {
        _showEvent.publishEvent(idError)
    }

    private fun showSnackbar(id : Long) {
        _showUndoSnackbar.publishEvent(id)
    }

    private fun showProgressItem(id: Long, isInProgress: Boolean) {
        if(isInProgress) {
            userIdsInProgress.add(id)
        } else {
            userIdsInProgress.remove(id)
        }

        val state = _favoriteItemsUiStateLiveDate.value
        state?.listItem?.forEach { item ->
            if(item.id == id) {
                item.isInProgress = isInProgress
            }

        }

        _favoriteItemsUiStateLiveDate.value = state!!
    }
}

data class FavoriteUiState(
    val isLoading: Boolean = false
)

data class FavoriteItemsUiState(
    val listItem : List<FavoriteItemUiState> = listOf()
)

data class FavoriteItemUiState(
    val id: Long,
    val name: String,
    var isBookmarked: Boolean = true,
    var isInProgress: Boolean = false
)

