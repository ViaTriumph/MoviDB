package com.practice.movidb.network.movie.service

import com.practice.movidb.network.common.ApiEndpoints
import com.practice.movidb.network.movie.data.model.PopularMovies
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET(ApiEndpoints.POPULAR_MOVIES)
    suspend fun getPopularMovies(): Response<PopularMovies>
}