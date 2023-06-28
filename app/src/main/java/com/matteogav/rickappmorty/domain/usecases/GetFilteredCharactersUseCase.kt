package com.matteogav.rickappmorty.domain.usecases

import com.matteogav.rickappmorty.domain.model.Character
import com.matteogav.rickappmorty.domain.repositories.CharacterRepository

class GetFilteredCharactersUseCase constructor(private val characterRepository: CharacterRepository) {

    suspend fun getCharactersFiltered(page: Int, filter: String): List<Character> = characterRepository.getCharactersFiltered(page, filter)

}