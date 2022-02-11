package com.example.starwarsapi.presentation.ui.main.character

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsapi.domain.Resource
import com.example.starwarsapi.domain.models.Character
import com.example.starwarsapi.domain.models.CharacterId
import com.example.starwarsapi.domain.usecases.GetCharacterDetailByIdUseCase
import com.example.starwarsapi.presentation.utils.MutableLiveEvent
import com.example.starwarsapi.presentation.utils.publishEvent
import com.example.starwarsapi.presentation.utils.share
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val getCharacterDetailByIdUseCase: GetCharacterDetailByIdUseCase
) : ViewModel() {

    private val _characterUiStateLiveData = MutableLiveData<CharacterUiState>()
    val characterUiStateLiveData = _characterUiStateLiveData.share()

    private val _showEvent = MutableLiveEvent<Int>()
    val showEvent = _showEvent.share()

    fun getCharacterDetail(characterId: CharacterId) {
        viewModelScope.launch {
            _characterUiStateLiveData.value = CharacterUiState(isLoading = true)
            val characterResult = getCharacterDetailByIdUseCase(characterId = characterId)
            when(characterResult) {
                is Resource.Success -> _characterUiStateLiveData.value = CharacterUiState(isLoading = false, character = characterResult.data)
                is Resource.Error -> showEvent(characterResult.message)
                is Resource.Loading -> _characterUiStateLiveData.value = CharacterUiState(isLoading = true)
            }

        }
    }

    private fun showEvent(msg: Int) {
        _showEvent.publishEvent(msg)
    }
}

data class CharacterUiState(
    val isLoading: Boolean = true,
    val character: Character? = null
)