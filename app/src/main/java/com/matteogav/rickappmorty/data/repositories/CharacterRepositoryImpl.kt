package com.matteogav.rickappmorty.data.repositories

import com.matteogav.rickappmorty.data.database.dao.CharacterDao
import com.matteogav.rickappmorty.data.database.entities.CharacterEntity
import com.matteogav.rickappmorty.domain.model.Character
import com.matteogav.rickappmorty.data.services.APIBaseService
import com.matteogav.rickappmorty.domain.model.toDomain
import com.matteogav.rickappmorty.domain.repositories.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CharacterRepositoryImpl (private val characterDao: CharacterDao): CharacterRepository {

    private val characterService = APIBaseService.characterService

    override suspend fun getCharactersFromAPI(page: Int): List<Character> {
        return withContext(Dispatchers.IO) {
            val result = characterService.getCharacters(page)
            val characters: List<Character> = result?.results?.map { it.toDomain(result.info) } ?: emptyList()
            return@withContext characters
        }
    }

    override suspend fun getCharactersFromDB(page: Int): List<Character> {
        return withContext(Dispatchers.IO) {
            val characters = characterDao.getCharacters(page)
            return@withContext characters.map { it.toDomain() }
        }
    }

    override suspend fun insertCharactersIntoDB(characters: List<CharacterEntity>) {
        characterDao.insertCharacters(characters)
    }

    override suspend fun clearCharactersFromDB(page: Int) {
        characterDao.clearCharacters(page)
    }

    override suspend fun getCharactersFiltered(page: Int, filter: String): List<Character> {
        return withContext(Dispatchers.IO) {
            val result = characterService.getCharactersFiltered(page, filter)
            val characters: List<Character> =
                result.results.filter {
                    it.species.equals(filter, ignoreCase = true)
                }.map {
                    it.toDomain(result.info)
                }
            return@withContext characters
        }
    }

    override suspend fun getCharactersSearch(page: Int, search: String): List<Character> {
        return withContext(Dispatchers.IO) {
            val result = characterService.getCharactersSearch(page, search)
            val characters: List<Character> = result.results.map { it.toDomain(result.info) }
            return@withContext characters
        }
    }

    override suspend fun getCharactersFilteredSearch(page: Int, filter: String, search: String): List<Character> {
        return withContext(Dispatchers.IO) {
            val result = characterService.getCharactersFilteredSearch(page, filter, search)
            val characters: List<Character> =
                result.results.filter {
                    it.species.equals(filter, ignoreCase = true)
                }.map {
                    it.toDomain(result.info)
                }
            return@withContext characters
        }
    }
}