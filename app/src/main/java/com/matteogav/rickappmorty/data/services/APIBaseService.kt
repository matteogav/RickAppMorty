package com.matteogav.rickappmorty.data.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIBaseService {
    private const val BASE_URL = "https://rickandmortyapi.com/api/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val characterService: CharacterService by lazy {
        retrofit.create(CharacterService::class.java)
    }

    val episodeService: EpisodeService by lazy {
        retrofit.create(EpisodeService::class.java)
    }
}