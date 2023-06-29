package com.matteogav.rickappmorty.domain.usecases

import com.matteogav.rickappmorty.domain.model.Character
import com.matteogav.rickappmorty.domain.repositories.CharacterRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test

class GetFilteredCharactersUseCaseTest {

    private lateinit var characterRepository: CharacterRepository
    private lateinit var getFilteredCharactersUseCase: GetFilteredCharactersUseCase

    @Before
    fun setUp() {
        characterRepository = mockk()
        getFilteredCharactersUseCase = GetFilteredCharactersUseCase(characterRepository)
    }

    fun createCharacter(id: Int): Character {
        return Character(
            id = id,
            created = "2017-11-04T18:48:46.250Z",
            episode = listOf(
                "https://rickandmortyapi.com/api/episode/1",
                "https://rickandmortyapi.com/api/episode/2",
            ),
            gender = "Male",
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            name = "Rick Sanchez",
            species = "Human",
            status = "Alive",
            type = "",
            url = "https://rickandmortyapi.com/api/character/1",
            originName = "Earth",
            originUrl = "https://rickandmortyapi.com/api/location/1",
            locationName = "Earth",
            locationUrl = "https://rickandmortyapi.com/api/location/20",
            infoNext = "https://rickandmortyapi.com/api/character/?page=2",
            infoPrev = null
        )
    }

    @Test
    fun `getCharactersFiltered should return filtered characters from repository`() = runBlocking {
        //GIVEN
        val page = 1
        val filter = "human"
        val filteredCharacters = listOf(createCharacter(1), createCharacter(2))

        coEvery { characterRepository.getCharactersFiltered(page, filter) } returns filteredCharacters

        //WHEN
        val result = getFilteredCharactersUseCase.getCharactersFiltered(page, filter)

        //THEN
        assertEquals(filteredCharacters.first().species, result.first().species)
        assertEquals(filteredCharacters, result)
    }

}