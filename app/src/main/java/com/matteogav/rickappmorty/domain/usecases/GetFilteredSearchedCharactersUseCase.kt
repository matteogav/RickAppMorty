package com.matteogav.rickappmorty.domain.usecases

import com.matteogav.rickappmorty.domain.model.Character
import com.matteogav.rickappmorty.domain.repositories.CharacterRepository

class GetFilteredSearchedCharactersUseCase constructor(private val characterRepository: CharacterRepository) {

    suspend fun getCharactersFilteredSearch(page: Int, filter: String, search: String): List<Character> = characterRepository.getCharactersFilteredSearch(page, filter, search)

}