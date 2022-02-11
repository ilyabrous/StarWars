package com.example.starwarsapi.data.repositories

import android.util.Log
import com.example.starwarsapi.data.DataHelper
import com.example.starwarsapi.data.remote.retrofit.CharacterApi
import com.example.starwarsapi.data.local.LocalDataStorage
import com.example.starwarsapi.data.local.wrapSQLiteException
import com.example.starwarsapi.domain.models.*
import com.example.starwarsapi.domain.repositories.CharacterRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CharacterRepositoryImpl(
    private val localDataStorage: LocalDataStorage,
    private val api: CharacterApi,
    private val ioDispatcher: CoroutineDispatcher
) : CharacterRepository {

    //use only api
    override suspend fun getRemoteCharactersByName(characterNameParam: CharacterNameParam): List<CharacterName> = withContext(ioDispatcher) {

        //todo may i add throw if name is empty? or add it logic into just use case?
        //think how i can thrown expection in repository??
        val listCharacterDto = api.getCharactersByName(name = characterNameParam.name).CharacterDto



        val listCharacterName = listCharacterDto.map { characterDto ->
            CharacterName(
                id = DataHelper.getIdFromUrl(characterDto.url),
                name = characterDto.name
            )
        }

        return@withContext listCharacterName
    }

    //use only local
    override suspend fun addCharacterToFavorite(character: Character) = wrapSQLiteException(ioDispatcher) {

        localDataStorage.saveLocalCharacter(character)

    }

    //use only local
    override suspend fun deleteCharacterFromFavoriteById(characterId: CharacterId) = wrapSQLiteException(ioDispatcher) {
        localDataStorage.deleteFavoriteCharacterById(id = characterId.id)
    }

    override suspend fun isCharacterSaved(characterId: CharacterId): Flow<Boolean> {
        return localDataStorage.isSavedCharacter(characterId.id)
    }

    //todo strange mapping. How to implement good mapping. if a class for model and domain is same?? where should i do mapping?
    //use only local
    override suspend fun getLocalAllFavoriteCharacters(): Flow<List<CharacterFavorite>> = wrapSQLiteException(ioDispatcher) {
        val flowOfList = localDataStorage.getLocalAllFavoriteCharacters()

        return@wrapSQLiteException flowOfList.map { list ->
          list.map { tuple ->
              CharacterFavorite(
                  id = tuple.id,
                  name = tuple.name
              )
          }
        }
    }

    //use only local
    override suspend fun getLocalCharacterDetailById(characterId: CharacterId) : Character = wrapSQLiteException(ioDispatcher) {
        val characterAndAllReferencesTuple = localDataStorage.getLocalCharactersDetailById(id = characterId.id)

        val films = characterAndAllReferencesTuple.films?.map { filmDbEntity ->
            Film(
                id = filmDbEntity.id,
                title =  filmDbEntity.title
            )
        }

        val species = characterAndAllReferencesTuple.species?.map { speciesDbEntity ->
            Species(
                id = speciesDbEntity.id,
                name =  speciesDbEntity.name
            )
        }

        val starships = characterAndAllReferencesTuple.starships?.map { starshipDbEntity ->
            Starship(
                id = starshipDbEntity.id,
                name =  starshipDbEntity.name
            )
        }

        val vehicles = characterAndAllReferencesTuple.vehicles?.map { vehiclesDbEntity ->
            Vehicle(
                id = vehiclesDbEntity.id,
                name =  vehiclesDbEntity.name
            )
        }



        return@wrapSQLiteException Character(
            id = characterAndAllReferencesTuple.characterDbEntity.id,
            birthYear = characterAndAllReferencesTuple.characterDbEntity.birthYear,
            created = characterAndAllReferencesTuple.characterDbEntity.created,
            edited = characterAndAllReferencesTuple.characterDbEntity.edited,
            eyeColor = characterAndAllReferencesTuple.characterDbEntity.eyeColor,
            films = films,
            gender = characterAndAllReferencesTuple.characterDbEntity.gender,
            hairColor = characterAndAllReferencesTuple.characterDbEntity.hairColor,
            height = characterAndAllReferencesTuple.characterDbEntity.height,
            homeWorld = characterAndAllReferencesTuple.characterDbEntity.homeWorld,
            mass = characterAndAllReferencesTuple.characterDbEntity.mass,
            name =characterAndAllReferencesTuple.characterDbEntity.name,
            skinColor = characterAndAllReferencesTuple.characterDbEntity.skinColor,
            species = species,
            starships = starships,
            url = characterAndAllReferencesTuple.characterDbEntity.url,
            vehicles = vehicles
        )
    }

    //use only api good
    override suspend fun getRemoteCharacterDetailById(characterId: CharacterId) : Character = withContext(ioDispatcher) {
        val characterDto = api.getCharacterById(characterId = characterId.id.toString())

        //mapping dto -> domainLayer
        val films : List<Film>? = if (characterDto.filmsUrls.isNotEmpty()) {
            characterDto.filmsUrls
                .map { filmUrl ->
                    val filmId = DataHelper.getIdFromUrl(filmUrl).toString()
                    val film = api.getFilmById(filmId)
                    Film(
                        id = filmId.toLong(),
                        title = film.title
                    )
                }
        } else {
            null
        }

        val species = if (characterDto.speciesUrls.isNotEmpty()) {
            characterDto.speciesUrls
                .map { speciesUrl ->
                    val speciesId = DataHelper.getIdFromUrl(speciesUrl).toString()
                    val species = api.getSpeciesById(speciesId)
                    Species(
                        id = speciesId.toLong(),
                        name = species.name
                    )
                }
        } else {
            null
        }

        val vehicles = if (characterDto.vehiclesUrls.isNotEmpty()) {
            characterDto.vehiclesUrls
                .map { vehicleUrl ->
                    val vehicleId = DataHelper.getIdFromUrl(vehicleUrl).toString()
                    val vehicle = api.getVehicleById(vehicleId)
                    Vehicle(
                        id = vehicleId.toLong(),
                        name = vehicle.name
                    )
                }
        } else {
            null
        }

        val starships = if (characterDto.starshipsUrls.isNotEmpty()) {
            characterDto.starshipsUrls
                .map { starshipUrl ->
                    val starshipId = DataHelper.getIdFromUrl(starshipUrl).toString()
                    val starship = api.getStarshipById(starshipId)
                    Starship(
                        id = starshipId.toLong(),
                        name = starship.name
                    )
                }
        } else {
            null
        }

        val homeWorldDto = api.getHomeWorldById(
            DataHelper.getIdFromUrl(characterDto.homeWorld).toString()
        )

        val homeWorld = homeWorldDto.name


        return@withContext Character(
            id = DataHelper.getIdFromUrl(characterDto.url),
            birthYear = characterDto.birthYear,
            created = characterDto.created,
            edited = characterDto.edited,
            eyeColor = characterDto.eyeColor,
            films = films,
            gender = characterDto.gender,
            hairColor = characterDto.hairColor,
            height = characterDto.height,
            homeWorld = homeWorld,
            mass = characterDto.mass,
            name = characterDto.name,
            skinColor = characterDto.skinColor,
            species = species,
            starships = starships,
            url = characterDto.url,
            vehicles = vehicles
        )
    }


}