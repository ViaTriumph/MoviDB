package com.practice.movidb.shared.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PopularMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) //Replace as popularity may change
    fun storePopularMovieIds(list: List<PopularMoviesEntity>)

    //TODO draw flow diagram for this
    /**
     * Get movies from central table [MovieEntity] which are present in popular list
     */
    @Query("SELECT DISTINCT * FROM popular_movies INNER JOIN movie ON popular_movies.movie_id = movie.movie_id ORDER BY movie.popularity")
    fun getPopularMovies(): List<MovieEntity>

    @Query("DELETE FROM popular_movies")
    fun deleteAll()
}