package com.matteogav.rickappmorty.domain.usecases

import com.matteogav.rickappmorty.domain.repositories.CharacterRepository
import com.matteogav.rickappmorty.domain.model.Character
import com.matteogav.rickappmorty.domain.model.toEntity
import io.mockk.coEvery
import io.mockk.coVerify
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
    fun `getCharacters returns characters from database if it's not empty`() = runBlocking {

        //GIVEN
        val dbCharacters = listOf(createCharacter(1), createCharacter(2))
        coEvery { characterRepository.getCharactersFromDB(1) } returns dbCharacters

        //WHEN
        val characters = getCharactersUseCase.getCharacters(1)

        //THEN
        assertEquals(dbCharacters, characters)
    }

    @Test
    fun `getCharacters fetches characters from API and updates database if it's empty`() = runBlocking {

        //GIVEN
        coEvery { characterRepository.getCharactersFromDB(1) } returns emptyList()

        val apiCharacters = listOf(createCharacter(3), createCharacter(4))
        coEvery { characterRepository.getCharactersFromAPI(1) } returns apiCharacters
        coEvery { characterRepository.clearCharactersFromDB(1) } returns Unit
        coEvery { characterRepository.insertCharactersIntoDB(any()) } returns Unit

        //WHEN
        val characters = getCharactersUseCase.getCharacters(1)

        //THEN
        assertEquals(apiCharacters, characters)
        coVerify(exactly = 1) { characterRepository.getCharactersFromDB(1) }
        coVerify(exactly = 1) { characterRepository.clearCharactersFromDB(1) }
        coVerify(exactly = 1) { characterRepository.insertCharactersIntoDB(apiCharacters.map { it.toEntity() }) }
    }
}