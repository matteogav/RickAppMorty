package com.matteogav.rickappmorty.app.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.matteogav.rickappmorty.domain.model.Episode
import com.matteogav.rickappmorty.domain.usecases.GetEpisodesUseCase
import io.mockk.coEvery
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*

class EpisodesViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var episodesViewModel: EpisodesViewModel
    private lateinit var getEpisodesUseCase: GetEpisodesUseCase

    private val episodesIds = listOf(1, 2, 3)

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        getEpisodesUseCase = mockk()
        episodesViewModel = EpisodesViewModel(getEpisodesUseCase)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
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
    fun `getEpisodes should update episodes when getEpisodesUseCase returns a non empty result`() = runTest {
        //GIVEN
        val episodes = listOf(createEpisode(1), createEpisode(2), createEpisode(3))
        coEvery { getEpisodesUseCase.getEpisodes(episodesIds) } returns episodes

        //WHEN
        episodesViewModel.getEpisodes(episodesIds)

        //THEN
        testDispatcher.scheduler.advanceUntilIdle()
        val result = episodesViewModel.episodes.value
        assertEquals(result, episodes)
    }

    @Test
    fun `getEpisodes should not update episodes when getEpisodesUseCase returns an empty result`() = runTest {
        //GIVEN
        coEvery { getEpisodesUseCase.getEpisodes(episodesIds) } returns emptyList()

        //WHEN
        episodesViewModel.getEpisodes(episodesIds)

        //THEN
        testDispatcher.scheduler.advanceUntilIdle()
        val result = episodesViewModel.episodes.value
        assertNull(result)
    }
}