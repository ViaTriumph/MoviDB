package com.practice.shared.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(sessions: List<MovieEntity>)

    @Query("SELECT * FROM movie ORDER BY vote_average DESC")
    fun getSearchList(): List<MovieEntity>

    @Query("SELECT * FROM movie WHERE movie.id in (:list)") //TODO try using junction table
    fun getSimilarMovies(list: List<Int>): List<MovieEntity>

    @Query("SELECT DISTINCT * FROM similar_movie WHERE similar_movie.movie_id = :id")
    fun getSimilarMovieIds(id: Int): SimilarMovieEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(similarMovies: SimilarMovieEntity)
}