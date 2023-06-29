package com.matteogav.rickappmorty.data.repositories

import com.matteogav.rickappmorty.data.database.dao.EpisodeDao
import com.matteogav.rickappmorty.data.database.entities.EpisodeEntity
import com.matteogav.rickappmorty.data.model.EpisodeModel
import com.matteogav.rickappmorty.data.services.APIBaseService
import com.matteogav.rickappmorty.domain.model.Episode
import com.matteogav.rickappmorty.domain.model.toDomain
import com.matteogav.rickappmorty.domain.repositories.EpisodesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EpisodesRepositoryImpl(private val episodeDao: EpisodeDao) : EpisodesRepository {

    private val episodeService = APIBaseService.episodeService

    override suspend fun getEpisodesFromAPI(episodes: List<Int>): List<Episode> {
        return withContext(Dispatchers.IO) {
            val episodesString: String = episodes.joinToString(",")
            val episodes: List<EpisodeModel>? = episodeService.getEpisodes(episodesString)
            return@withContext episodes?.map { it.toDomain() } ?: emptyList()
        }
    }

    override suspend fun getEpisodesFromDB(episodes: List<Int>): List<Episode> {
        return withContext(Dispatchers.Default) {
            val episodes = episodeDao.getEpisodes(episodes)
            return@withContext episodes.map { it.toDomain() }
        }
    }

    override suspend fun insertEpisodesIntoDB(episodes: List<EpisodeEntity>) {
        episodeDao.insertEpisodes(episodes)
    }
1
    override suspend fun clearEpisodesFromDB(episodes: List<Int>) {
        episodeDao.clearEpisodes(episodes)
    }
}