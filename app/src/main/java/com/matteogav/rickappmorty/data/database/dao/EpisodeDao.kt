package com.matteogav.rickappmorty.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.matteogav.rickappmorty.data.database.entities.EpisodeEntity

@Dao
interface EpisodeDao {

    @Query("SELECT * FROM episodes_table WHERE id IN (:episodes) ORDER BY id ASC")
    suspend fun getEpisodes(episodes: List<Int>): List<EpisodeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisodes(episodes: List<EpisodeEntity>)

    @Query("DELETE FROM episodes_table WHERE id IN (:episodes)")
    suspend fun clearEpisodes(episodes: List<Int>)
}