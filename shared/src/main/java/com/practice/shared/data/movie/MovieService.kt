package com.practice.shared.data.movie

import com.practice.shared.data.network.BaseResult
import retrofit2.http.GET

interface MovieService {

    @GET(com.practice.shared.data.network.ApiEndpoints.POPULAR_MOVIES)
    suspend fun getPopularMovies(): BaseResult<MovieList>

    @GET(com.practice.shared.data.network.ApiEndpoints.NOW_PLAYING_MOVIES)
    suspend fun getNowPlayingMovies(): BaseResult<MovieList>
}