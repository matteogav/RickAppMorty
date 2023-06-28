package com.matteogav.rickappmorty.domain.repositories

import com.matteogav.rickappmorty.data.database.entities.EpisodeEntity
import com.matteogav.rickappmorty.domain.model.Episode

interface EpisodesRepository {
    suspend fun getEpisodesFromAPI(episodes: List<Int>): List<Episode>
    suspend fun getEpisodesFromDB(episodes: List<Int>): List<Episode>

    suspend fun insertEpisodesIntoDB(episodes: List<EpisodeEntity>)
    suspend fun clearEpisodesFromDB(episodes: List<Int>)
}