package com.example.starwarsapi.domain.usecases

import android.util.Log
import com.example.starwarsapi.R
import com.example.starwarsapi.domain.Resource
import com.example.starwarsapi.domain.StorageException
import com.example.starwarsapi.domain.models.Character
import com.example.starwarsapi.domain.models.CharacterId
import com.example.starwarsapi.domain.repositories.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.lang.Exception
import javax.inject.Inject

class GetCharacterDetailByIdUseCase @Inject constructor (
    private val characterRepository: CharacterRepository,
) {

    suspend operator fun invoke(characterId: CharacterId) : Resource<Character> {
        var character : Character
        return try {
            character = characterRepository.getLocalCharacterDetailById(characterId = CharacterId(characterId.id))
            Resource.Success(character)
        } catch (e: Exception) {
            Log.d("AAA", "getting local character thrown error. Try get remote")
            character = characterRepository.getRemoteCharacterDetailById(characterId = CharacterId(characterId.id))
            Resource.Success(character)
        } catch (e: HttpException) {
            Resource.Error(R.string.internet_error)
        } catch (e: StorageException) {
            Resource.Error(R.string.local_error)
        } catch (e: Exception) {
            Resource.Error(R.string.unexpected_error)
        }

    }
}