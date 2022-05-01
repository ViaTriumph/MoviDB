package com.practice.shared.data.db

import androidx.room.*

@Dao
interface PopularMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) //Replace as popularity may change
    fun storePopularMovieIds(list: List<PopularMoviesEntity>)

    //TODO draw flow diagram for this
    /**
     * Get movies from central table [MovieEntity] which are present in popular list
     */
    @Query("SELECT DISTINCT * FROM popular_movies INNER JOIN movie ON popular_movies.movie_id = movie.id ORDER BY movie.popularity DESC LIMIT 20")
    fun getPopularMovies(): List<MovieEntity>

    @Query("DELETE FROM popular_movies")
    fun deleteAll()
}