package com.practice.shared.domain.movie

import com.practice.shared.data.network.BaseResult
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(): Flow<BaseResult<MovieList>>
    fun getNowPlayingMovies(): Flow<BaseResult<MovieList>>
}