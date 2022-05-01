package com.practice.shared.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDetailDao {

    @Query("SELECT * FROM movie_detail WHERE movie_detail.id = :movieId")
    fun getMovieDetails(movieId: Int): MovieDetailEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieDetail(details: MovieDetailEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<MovieDetailEntity>)
}