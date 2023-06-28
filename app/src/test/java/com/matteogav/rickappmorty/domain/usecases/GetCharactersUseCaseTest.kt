package com.matteogav.rickappmorty.domain.usecases

import com.matteogav.rickappmorty.data.model.*
import com.matteogav.rickappmorty.domain.repositories.CharacterRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetCharactersUseCaseTest {

    private lateinit var characterRepository: CharacterRepository
    private lateinit var getCharactersUseCase: GetCharactersUseCase

    @Before
    fun setup() {
        characterRepository = mockk()
        getCharactersUseCase = GetCharactersUseCase(characterRepository)
    }

/*    @Test
    fun `test getCharacters() returns expected result`() = runBlocking {
        val expectedCharacter = CharacterModel(
            InfoModel(
                826,
                "https://rickandmortyapi.com/api/character/?page=2",
                42,
                null
            ),
            listOf(
                ResultModel(
                    "2017-11-04T18:48:46.250Z",
                    listOf("https://rickandmortyapi.com/api/episode/1"),
                    "Male",
                    1,
                    "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                    LocationModel("Earth", "https://rickandmortyapi.com/api/location/20"),
                    "Rick Sanchez",
                    OriginModel("Earth", "https://rickandmortyapi.com/api/location/1"),
                    "Human",
                    "Alive",
                    "",
                    "https://rickandmortyapi.com/api/character/1",

                )
            )
        )
        val page = 1
        coEvery { characterRepository.getCharactersFromAPI(page) } returns expectedCharacter

        // Act
        val result = getCharactersUseCase.getCharacters(page)

        // Assert
        assertEquals(expectedCharacter, result)
    }*/
}