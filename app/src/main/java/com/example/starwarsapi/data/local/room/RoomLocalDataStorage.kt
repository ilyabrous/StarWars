package com.example.starwarsapi.data.local.room

import com.example.starwarsapi.data.local.LocalDataStorage
import com.example.starwarsapi.data.local.room.dao.*
import com.example.starwarsapi.data.local.room.entities.*
import com.example.starwarsapi.data.local.room.entities.crossRef.CharacterFilmCrossRef
import com.example.starwarsapi.data.local.room.entities.crossRef.CharacterSpeciesCrossRef
import com.example.starwarsapi.data.local.room.entities.crossRef.CharacterStarshipCrossRef
import com.example.starwarsapi.data.local.room.entities.crossRef.CharacterVehicleCrossRef
import com.example.starwarsapi.domain.models.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocalDataStorage(
    private val characterDao: CharacterDao,
    private val filmDao: FilmDao,
    private val speciesDao: SpeciesDao,
    private val starshipDao: StarshipDao,
    private val vehicleDao: VehicleDao,
) : LocalDataStorage {

    override suspend fun saveLocalCharacter(character: Character) {
        val characterDbEntity = CharacterDbEntity(
            id = character.id,
            birthYear = character.birthYear,
            created = character.created,
            edited = character.edited,
            eyeColor = character.eyeColor,
            gender = character.gender,
            hairColor = character.hairColor,
            height = character.height,
            homeWorld = character.homeWorld,
            mass = character.mass,
            name = character.name,
            skinColor = character.skinColor,
            url = character.url
        )

        val listFilmDbEntity : List<FilmDbEntity>? = character.films
            ?.map { film ->
                FilmDbEntity(
                    id = film.id,
                    title = film.title
                )
            }

        val listSpeciesDbEntity : List<SpeciesDbEntity>? = character.species
            ?.map{ species ->
                SpeciesDbEntity(
                    id = species.id,
                    name = species.name,
                )
            }

        val listStarshipDbEntity : List<StarshipDbEntity>? = character.starships
            ?.map{ starship ->
                StarshipDbEntity(
                    id = starship.id,
                    name = starship.name
                )
            }


        val listVehicleDbEntity : List<VehicleDbEntity>? = character.vehicles
            ?.map{ vehicle ->
            VehicleDbEntity(
                id = vehicle.id,
                name = vehicle.name
            )
            }

        characterDao.createAccount(characterDbEntity)
        if(listFilmDbEntity != null) filmDao.createFilm(listFilmDbEntity)
        if(listSpeciesDbEntity != null) speciesDao.createSpecies(listSpeciesDbEntity)
        if(listStarshipDbEntity != null) starshipDao.createStarship(listStarshipDbEntity)
        if(listVehicleDbEntity != null) vehicleDao.createVehicle(listVehicleDbEntity)



        val listFilmCrossRef = listFilmDbEntity?.map { entity ->

            CharacterFilmCrossRef(
                characterId = characterDbEntity.id,
                filmId = entity.id
            )
        }

        val listSpeciesCrossRef = listSpeciesDbEntity?.map { entity ->
            CharacterSpeciesCrossRef(
                characterId = characterDbEntity.id,
                speciesId = entity.id
            )
        }

        val listStarshipCrossRef = listStarshipDbEntity?.map { entity ->
            CharacterStarshipCrossRef(
                characterId = characterDbEntity.id,
                starshipId = entity.id
            )
        }

        val listVehicleCrossRef = listVehicleDbEntity?.map { entity ->
            CharacterVehicleCrossRef(
                characterId = characterDbEntity.id,
                vehicleId = entity.id
            )
        }



        characterDao.createFullCharacterRelation(
            characterFilmCrossRef = listFilmCrossRef,
            characterSpeciesCrossRef = listSpeciesCrossRef,
            characterStarshipCrossRef = listStarshipCrossRef,
            characterVehicleCrossRef = listVehicleCrossRef
        )

    }

    override suspend fun getLocalAllFavoriteCharacters(): Flow<List<CharacterFavoriteTuple>> {
        return characterDao.getAllFavoriteCharacters()
    }

    //todo replace mb? id->CharacterId
    override suspend fun getLocalCharactersDetailById(id: Long): CharacterAndAllReferencesTuple {
        return characterDao.getCharacterById(characterId = id)
    }

    override suspend fun deleteFavoriteCharacterById(id: Long) {
        characterDao.deleteAccountById(id = id)
    }

    override suspend fun isSavedCharacter(id: Long): Flow<Boolean> {
        return characterDao.getCountCharacterById(id).map {
            it > 0
        }
    }


}