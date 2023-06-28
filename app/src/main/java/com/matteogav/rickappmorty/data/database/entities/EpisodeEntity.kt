package com.matteogav.rickappmorty.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "episodes_table")
data class EpisodeEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "air_date") val air_date: String,
    @ColumnInfo(name = "characters") val characters: List<String>,
    @ColumnInfo(name = "created") val created: String,
    @ColumnInfo(name = "episode") val episode: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "url") val url: String
)