package com.practice.shared.domain.details

import com.practice.shared.data.network.BaseResult
import com.practice.shared.domain.movie.MovieList
import kotlinx.coroutines.flow.Flow

interface MovieDetailsRepository {

    fun getMovieDetail(id: Int): Flow<BaseResult<MovieDetail>>

    fun getSimilarMovies(id: Int): Flow<BaseResult<MovieList>>
}