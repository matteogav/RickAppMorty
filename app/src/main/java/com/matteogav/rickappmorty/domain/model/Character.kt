package com.matteogav.rickappmorty.domain.model

import com.matteogav.rickappmorty.data.database.entities.CharacterEntity
import com.matteogav.rickappmorty.data.model.InfoModel
import com.matteogav.rickappmorty.data.model.ResultModel
import kotlinx.serialization.Serializable

@Serializable
data class Character(
    val id: Int,
    val created: String,
    val episode: List<String>,
    val gender: String,
    val image: String,
    val name: String,
    val species: String,
    val status: String,
    val type: String,
    val url: String,
    val originName: String,
    val originUrl: String,
    val locationName: String,
    val locationUrl: String,
    val infoNext: String,
    val infoPrev: String?
)


fun ResultModel.toDomain(info: InfoModel) = Character(id, created, episode, gender, image, name, species, status, type, url, origin.name, origin.url, location.name, location.url, info.next, info.prev)

fun CharacterEntity.toDomain() = Character(id, created, episode, gender, image, name, species, status, type, url, originName, originUrl, locationName, locationUrl, infoNext, infoPrev)

fun Character.toEntity() = CharacterEntity(id, created, episode, gender, image, name, species, status,
                                            type, url, originName, originUrl, locationName, locationUrl,
                                            infoPrev?.substringAfterLast("page=")?.toInt()?.plus(1) ?: 1, infoNext, infoPrev)

