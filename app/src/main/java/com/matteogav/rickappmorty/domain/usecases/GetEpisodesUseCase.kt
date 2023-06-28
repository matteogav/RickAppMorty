package com.matteogav.rickappmorty.domain.usecases

import com.matteogav.rickappmorty.domain.model.Episode
import com.matteogav.rickappmorty.domain.model.toEntity
import com.matteogav.rickappmorty.domain.repositories.EpisodesRepository

class GetEpisodesUseCase constructor(private val episodesRepository: EpisodesRepository) {

    suspend fun getEpisodes(episodes: List<Int>): List<Episode> {

        val result: List<Episode> = episodesRepository.getEpisodesFromDB(episodes)

        return if (result.isEmpty() || result.size != episodes.size) {
            val apiResult = episodesRepository.getEpisodesFromAPI(episodes)
            episodesRepository.clearEpisodesFromDB(episodes)
            episodesRepository.insertEpisodesIntoDB(apiResult.map { it.toEntity() })
            apiResult
        }  else {
            result
        }
    }
}