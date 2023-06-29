package com.matteogav.rickappmorty.domain.usecases

import com.matteogav.rickappmorty.domain.model.Character
import com.matteogav.rickappmorty.domain.repositories.CharacterRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetFilteredSearchedCharactersUseCaseTest {
    private lateinit var characterRepository: CharacterRepository
    private lateinit var getFilteredSearchedCharactersUseCase: GetFilteredSearchedCharactersUseCase

    @Before
    fun setUp() {
        characterRepository = mockk()
        getFilteredSearchedCharactersUseCase = GetFilteredSearchedCharactersUseCase(characterRepository)
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
    fun `getCharactersFilteredSearch should return filtered and searched characters from repository`() = runBlocking {
        //GIVEN
        val page = 1
        val filter = "human"
        val search = "rick"
        val filteredSearchedCharacters = listOf(createCharacter(1), createCharacter(2))

        coEvery { characterRepository.getCharactersFilteredSearch(page, filter, search) } returns filteredSearchedCharacters

        //WHEN
        val result = getFilteredSearchedCharactersUseCase.getCharactersFilteredSearch(page, filter, search)

        //THEN
        assertEquals(filteredSearchedCharacters.first().species, result.first().species)
        assertTrue(result.first().name.lowercase().contains(search.lowercase()))
        assertEquals(filteredSearchedCharacters, result)
    }
}