import com.matteogav.rickappmorty.data.model.EpisodeModel
import com.matteogav.rickappmorty.domain.repositories.EpisodesRepository
import com.matteogav.rickappmorty.domain.usecases.GetEpisodesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetEpisodesUseCaseTest {

    private lateinit var episodesRepository: EpisodesRepository
    private lateinit var getEpisodesUseCase: GetEpisodesUseCase

    @Before
    fun setup() {
        episodesRepository = mockk()
        getEpisodesUseCase = GetEpisodesUseCase(episodesRepository)
    }
/*
    @Test
    fun `test getEpisodes() returns expected result`() = runBlocking {
        val expectedEpisodes = listOf(
            EpisodeModel(
                air_date = "2013-12-02",
                characters = listOf("https://rickandmortyapi.com/api/character/1", "https://rickandmortyapi.com/api/character/2"),
                created = "2017-11-10T12:56:33.798Z",
                episode = "S01E01",
                id = 1,
                name = "Pilot",
                url = "https://rickandmortyapi.com/api/episode/1"
            ),
            EpisodeModel(
                air_date = "2013-12-09",
                characters = listOf("https://rickandmortyapi.com/api/character/1", "https://rickandmortyapi.com/api/character/2"),
                created = "2017-11-10T12:56:33.916Z",
                episode = "S01E02",
                id = 2,
                name = "Lawnmower Dog",
                url = "https://rickandmortyapi.com/api/episode/2"
            )
        )
        val episodeUrls = "1,2"
        coEvery { episodesRepository.getEpisodesFromAPI(episodeUrls) } returns expectedEpisodes

        // Act
        val result = getEpisodesUseCase.getEpisodes(episodeUrls)

        // Assert
        assertEquals(expectedEpisodes, result)
    }*/
}
