package com.practice.movidb.network.movie.service

import com.practice.movidb.network.common.ApiEndpoints
import com.practice.movidb.network.movie.model.PopularMovies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET(ApiEndpoints.POPULAR_MOVIES)
    fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("region") region: String
    ): Call<PopularMovies>
}