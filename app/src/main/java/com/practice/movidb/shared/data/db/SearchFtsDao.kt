package com.practice.movidb.shared.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface SearchFtsDao {

    @Transaction //TODO what is transaction
    @Query(
        """
        SELECT DISTINCT * FROM movie 
        INNER JOIN movie_fts ON movie.id = movie_fts.id
        WHERE movie_fts MATCH :query ORDER BY popularity DESC
    """
    )
    fun getSearchList(query: String): List<MovieEntity>
}