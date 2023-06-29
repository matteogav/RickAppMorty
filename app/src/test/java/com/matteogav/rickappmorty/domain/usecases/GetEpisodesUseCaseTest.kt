package com.matteogav.rickappmorty.domain.usecases

import com.matteogav.rickappmorty.domain.model.Episode
import com.matteogav.rickappmorty.domain.model.toEntity
import com.matteogav.rickappmorty.domain.repositories.EpisodesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetEpisodesUseCaseTest {

    private lateinit var episodesRepository: EpisodesRepository
    private lateinit var getEpisodesUseCase: GetEpisodesUseCase

    private val episodesIds = listOf(1, 2, 3)

    @Before
    fun setup() {
        episodesRepository = mockk()
        getEpisodesUseCase = GetEpisodesUseCase(episodesRepository)
    }

    fun createEpisode(id: Int): Episode {
        return Episode(
            air_date = "September 10, 2017",
            characters = listOf(
                "https://rickandmortyapi.com/api/character/1",
                "https://rickandmortyapi.com/api/character/2"
            ),
            created = "2017-11-10T12:56:36.618Z",
            episode = "S03E07",
            id = id,
            name = "The Ricklantis Mixup",
            url = "https://rickandmortyapi.com/api/episode/28"
        )
    }

    @Test
    fun `getEpisodes returns episodes from database if it's not empty`() = runBlocking {

        //GIVEN
        val dbEpisodes = listOf(createEpisode(1), createEpisode(2), createEpisode(3))

        coEvery { episodesRepository.getEpisodesFromDB(episodesIds) } returns dbEpisodes
        coEvery { episodesRepository.getEpisodesFromAPI(any()) } returns emptyList()
        coEvery { episodesRepository.clearEpisodesFromDB(any()) } returns Unit
        coEvery { episodesRepository.insertEpisodesIntoDB(any()) } returns Unit

        //WHEN
        val episodes = getEpisodesUseCase.getEpisodes(episodesIds)

        //THEN
        assertEquals(dbEpisodes, episodes)
        coVerify(exactly = 1) { episodesRepository.getEpisodesFromDB(episodesIds) }
        coVerify(exactly = 0) { episodesRepository.getEpisodesFromAPI(any()) }
        coVerify(exactly = 0) { episodesRepository.clearEpisodesFromDB(any()) }
        coVerify(exactly = 0) { episodesRepository.insertEpisodesIntoDB(any()) }
    }

    @Test
    fun `getEpisodes fetches episodes from API and updates database if it's empty`() = runBlocking {

        //GIVEN
        val dbEpisodes = emptyList<Episode>()
        val apiEpisodes = listOf(createEpisode(1), createEpisode(2), createEpisode(3))

        coEvery { episodesRepository.getEpisodesFromDB(episodesIds) } returns dbEpisodes
        coEvery { episodesRepository.getEpisodesFromAPI(episodesIds) } returns apiEpisodes
        coEvery { episodesRepository.clearEpisodesFromDB(episodesIds) } returns Unit
        coEvery { episodesRepository.insertEpisodesIntoDB(any()) } returns Unit

        //WHEN
        val episodes = getEpisodesUseCase.getEpisodes(episodesIds)

        //THEN
        assertEquals(apiEpisodes, episodes)
        coVerify(exactly = 1) { episodesRepository.getEpisodesFromDB(episodesIds) }
        coVerify(exactly = 1) { episodesRepository.getEpisodesFromAPI(episodesIds) }
        coVerify(exactly = 1) { episodesRepository.clearEpisodesFromDB(episodesIds) }
        coVerify(exactly = 1) { episodesRepository.insertEpisodesIntoDB(apiEpisodes.map { it.toEntity() }) }
    }

    @Test
    fun `getEpisodes fetches episodes from API and updates database if result size doesn't match episodes list size`() = runBlocking {

        //GIVEN
        val dbEpisodes = listOf(createEpisode(1), createEpisode(2))
        val apiEpisodes = listOf(createEpisode(1), createEpisode(2), createEpisode(3))

        coEvery { episodesRepository.getEpisodesFromDB(episodesIds) } returns dbEpisodes
        coEvery { episodesRepository.getEpisodesFromAPI(episodesIds) } returns apiEpisodes
        coEvery { episodesRepository.clearEpisodesFromDB(episodesIds) } returns Unit
        coEvery { episodesRepository.insertEpisodesIntoDB(any()) } returns Unit

        //WHEN
        val episodes = getEpisodesUseCase.getEpisodes(episodesIds)

        //THEN
        assertEquals(apiEpisodes, episodes)
        coVerify(exactly = 1) { episodesRepository.getEpisodesFromDB(episodesIds) }
        coVerify(exactly = 1) { episodesRepository.getEpisodesFromAPI(episodesIds) }
        coVerify(exactly = 1) { episodesRepository.clearEpisodesFromDB(episodesIds) }
        coVerify(exactly = 1) { episodesRepository.insertEpisodesIntoDB(apiEpisodes.map { it.toEntity() }) }
    }

}
