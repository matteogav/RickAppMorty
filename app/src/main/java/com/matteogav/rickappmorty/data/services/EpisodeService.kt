package com.matteogav.rickappmorty.data.services

import retrofit2.http.GET
import com.matteogav.rickappmorty.data.model.EpisodeModel
import retrofit2.http.Path

interface EpisodeService {
    @GET("episode/{episodes}")
    suspend fun getEpisodes(@Path("episodes") episodes: String): List<EpisodeModel>?
}