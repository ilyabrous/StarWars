package com.example.starwarsapi.presentation.ui.main.character

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsapi.domain.Resource
import com.example.starwarsapi.domain.models.Character
import com.example.starwarsapi.domain.models.CharacterId
import com.example.starwarsapi.domain.usecases.GetCharacterDetailByIdUseCase
import com.example.starwarsapi.presentation.utils.share
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val getCharacterDetailByIdUseCase: GetCharacterDetailByIdUseCase
) : ViewModel() {

    private val _characterLiveData = MutableLiveData<Character>()
    val characterLiveData = _characterLiveData.share()

    fun getCharacterDetail(characterId: CharacterId) {
        viewModelScope.launch {
            val characterResult = getCharacterDetailByIdUseCase(characterId = characterId)

            if (characterResult is Resource.Success) {
                characterResult.data.let {
                    _characterLiveData.value = it
                }
            }
        }
    }
}