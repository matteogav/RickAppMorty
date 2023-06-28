package com.matteogav.rickappmorty.domain.usecases

import com.matteogav.rickappmorty.domain.repositories.CharacterRepository
import com.matteogav.rickappmorty.domain.model.Character
import com.matteogav.rickappmorty.domain.model.toEntity

class GetCharactersUseCase constructor(private val characterRepository: CharacterRepository) {

    suspend fun getCharacters(page: Int): List<Character> {

        val result: List<Character> = characterRepository.getCharactersFromDB(page)

        return result.ifEmpty {
            val apiResult = characterRepository.getCharactersFromAPI(page)
            characterRepository.clearCharactersFromDB(page)
            characterRepository.insertCharactersIntoDB(apiResult.map { it.toEntity() })
            apiResult
        }
    }

}