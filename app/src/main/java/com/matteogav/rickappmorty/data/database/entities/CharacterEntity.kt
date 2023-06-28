package com.matteogav.rickappmorty.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters_table")
data class CharacterEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "created") val created: String,
    @ColumnInfo(name = "episode") val episode: List<String>,
    @ColumnInfo(name = "gender") val gender: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "species") val species: String,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "originName") val originName: String,
    @ColumnInfo(name = "originUrl") val originUrl: String,
    @ColumnInfo(name = "locationName") val locationName: String,
    @ColumnInfo(name = "locationUrl") val locationUrl: String,
    @ColumnInfo(name = "page") val page: Int,
    @ColumnInfo(name = "infoNext") val infoNext: String,
    @ColumnInfo(name = "infoPrev") val infoPrev: String?
)