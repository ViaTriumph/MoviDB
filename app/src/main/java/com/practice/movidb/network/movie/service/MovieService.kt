package com.practice.movidb.network.movie.service

import com.practice.movidb.common.BaseResult
import com.practice.movidb.network.common.ApiEndpoints
import com.practice.movidb.network.movie.data.model.MovieList
import retrofit2.http.GET

interface MovieService {

    @GET(ApiEndpoints.POPULAR_MOVIES)
    suspend fun getPopularMovies(): BaseResult<MovieList>

    @GET(ApiEndpoints.NOW_PLAYING_MOVIES)
    suspend fun getNowPlayingMovies(): BaseResult<MovieList>
}