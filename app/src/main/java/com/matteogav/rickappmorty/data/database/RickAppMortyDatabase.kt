package com.matteogav.rickappmorty.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.matteogav.rickappmorty.data.database.dao.*
import com.matteogav.rickappmorty.data.database.entities.CharacterEntity
import com.matteogav.rickappmorty.data.database.entities.EpisodeEntity

@Database(entities =
            [
                EpisodeEntity::class,
                CharacterEntity::class,
            ],
            version = 1
            )
@TypeConverters(ListStringConverter::class)
abstract class RickAppMortyDatabase: RoomDatabase() {
    abstract fun episodeDao(): EpisodeDao
    abstract fun characterDao(): CharacterDao
}


class ListStringConverter {
    @TypeConverter
    fun fromListToString(list: List<String>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun fromStringToList(value: String): List<String> {
        return value.split(",").map { it.trim() }
    }
}