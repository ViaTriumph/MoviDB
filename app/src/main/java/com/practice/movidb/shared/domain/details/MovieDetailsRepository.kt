package com.practice.movidb.shared.domain.details

import com.practice.movidb.common.BaseResult
import com.practice.movidb.shared.domain.movie.MovieList
import kotlinx.coroutines.flow.Flow

interface MovieDetailsRepository {

    fun getMovieDetail(id: Int): Flow<BaseResult<MovieDetail>>

    fun getSimilarMovies(id: Int): Flow<BaseResult<MovieList>>
}