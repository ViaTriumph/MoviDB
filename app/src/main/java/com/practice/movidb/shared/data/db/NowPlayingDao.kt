package com.practice.movidb.shared.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NowPlayingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) // update now playing
    fun storeNowPlayingMovies(list: List<NowPlayingMovesEntity>)

    @Query("SELECT DISTINCT * FROM now_playing INNER JOIN movie ON now_playing.movie_id = movie.id ORDER BY movie.popularity DESC")
    fun getNowPlayingMovies(): List<MovieEntity>

    @Query("DELETE FROM now_playing")
    fun deleteAll()
}