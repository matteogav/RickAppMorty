package com.matteogav.rickappmorty.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.matteogav.rickappmorty.data.database.entities.CharacterEntity

@Dao
interface CharacterDao {

    @Query("SELECT * FROM characters_table WHERE page = :page ORDER BY id ASC")
    suspend fun getCharacters(page: Int): List<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<CharacterEntity>)


    @Query("DELETE FROM characters_table WHERE page = :page")
    suspend fun clearCharacters(page: Int)

}