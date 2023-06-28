package com.matteogav.rickappmorty.domain.model

import com.matteogav.rickappmorty.data.database.entities.EpisodeEntity
import com.matteogav.rickappmorty.data.model.EpisodeModel

data class Episode(
    val air_date: String,
    val characters: List<String>,
    val created: String,
    val episode: String,
    val id: Int,
    val name: String,
    val url: String
)

fun EpisodeModel.toDomain() = Episode(air_date, characters, created, episode, id, name, url)

fun EpisodeEntity.toDomain() = Episode(air_date, characters, created, episode, id, name, url)

fun Episode.toEntity() = EpisodeEntity(id, air_date, characters, created, episode, name, url)
