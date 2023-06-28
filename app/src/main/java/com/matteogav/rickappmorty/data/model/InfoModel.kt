package com.matteogav.rickappmorty.data.model

import kotlinx.serialization.Serializable

@Serializable
data class InfoModel(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: String?
)