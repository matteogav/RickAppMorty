package com.matteogav.rickappmorty.data.services

import retrofit2.http.GET
import retrofit2.http.Query
import com.matteogav.rickappmorty.data.model.CharacterModel

interface CharacterService {
    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): CharacterModel?

    @GET("character")
    suspend fun getCharactersFiltered(@Query("page") page: Int, @Query("species") species: String): CharacterModel?

    @GET("character")
    suspend fun getCharactersSearch(@Query("page") page: Int, @Query("name") name: String): CharacterModel?

    @GET("character")
    suspend fun getCharactersFilteredSearch(@Query("page") page: Int, @Query("species") species: String, @Query("name") name: String): CharacterModel?
}