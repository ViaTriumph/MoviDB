package com.practice.movidb.shared.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SearchFtsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(sessions: List<MovieEntity>)

    @Query("SELECT * FROM movie_fts ORDER BY voteAverage DESC")
    fun getSearchList(): List<MovieEntity>
}