package com.example.starwarsapi.presentation.di

import com.example.starwarsapi.domain.repositories.CharacterRepository
import com.example.starwarsapi.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


//@Module
//@InstallIn(ViewModelComponent::class)
//object DomainModule {
//
//    @Provides
//    fun provideGetCharacterByNameUseCase(repository: CharacterRepository) : GetCharactersByNameUseCase {
//        return GetCharactersByNameUseCase(characterRepository = repository)
//    }
//
//    @Provides
//    fun provideAddCharacterToFavoriteUseCase(repository: CharacterRepository) : AddCharacterToFavoriteUseCase {
//        return AddCharacterToFavoriteUseCase(characterRepository = repository)
//    }
//
//    @Provides
//    fun provideGetFavoriteCharactersUseCase(repository: CharacterRepository) : GetFavoriteCharactersUseCase {
//        return GetFavoriteCharactersUseCase(characterRepository = repository)
//    }
//
//    @Provides
//    fun provideDeleteCharacterFromFavoriteByIdUseCase(repository: CharacterRepository) : DeleteCharacterFromFavoriteByIdUseCase {
//        return DeleteCharacterFromFavoriteByIdUseCase(characterRepository = repository)
//    }
//
//    @Provides
//    fun provideGetCharacterDetailByIdUseCase(repository: CharacterRepository) : GetCharacterDetailByIdUseCase {
//        return GetCharacterDetailByIdUseCase(characterRepository = repository)
//    }
//}