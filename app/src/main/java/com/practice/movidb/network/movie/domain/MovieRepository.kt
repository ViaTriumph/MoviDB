package com.practice.movidb.network.movie.domain

import com.practice.movidb.common.BaseResult
import com.practice.movidb.shared.domain.movie.MovieList
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(): Flow<BaseResult<MovieList>>
    fun getNowPlayingMovies(): Flow<BaseResult<MovieList>>
}