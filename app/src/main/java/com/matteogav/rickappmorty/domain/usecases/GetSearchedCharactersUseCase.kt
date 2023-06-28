package com.matteogav.rickappmorty.domain.usecases

import com.matteogav.rickappmorty.domain.model.Character
import com.matteogav.rickappmorty.domain.repositories.CharacterRepository

class GetSearchedCharactersUseCase constructor(private val characterRepository: CharacterRepository) {

    suspend fun getCharactersSearch(page: Int, search: String): List<Character> = characterRepository.getCharactersSearch(page, search)

}