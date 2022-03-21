package com.practice.movidb.network.movie.domain

import com.practice.movidb.common.BaseResult
import com.practice.movidb.network.movie.domain.model.PopularMovies
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getPopularMovies(): Flow<BaseResult<PopularMovies>>
}