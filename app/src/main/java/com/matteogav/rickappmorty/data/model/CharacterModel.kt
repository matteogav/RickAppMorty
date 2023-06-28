package com.matteogav.rickappmorty.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CharacterModel(
    val info: InfoModel,
    val results: List<ResultModel>
)