package com.matteogav.rickappmorty.domain.repositories

import com.matteogav.rickappmorty.data.database.entities.CharacterEntity
import com.matteogav.rickappmorty.domain.model.Character

interface CharacterRepository {

    suspend fun getCharactersFromAPI(page: Int): List<Character>
    suspend fun getCharactersFromDB(page: Int): List<Character>

    suspend fun insertCharactersIntoDB(characters: List<CharacterEntity>)
    suspend fun clearCharactersFromDB(page: Int)

    suspend fun getCharactersFiltered(page: Int, filter: String): List<Character>

    suspend fun getCharactersSearch(page: Int, search: String): List<Character>

    suspend fun getCharactersFilteredSearch(page: Int, filter: String, search: String): List<Character>

}