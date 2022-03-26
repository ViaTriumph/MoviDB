package com.practice.movidb.shared.data.details

import com.practice.movidb.common.BaseResult
import com.practice.movidb.network.common.ApiEndpoints
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDetailService {

    @GET(ApiEndpoints.MOVIE_DETAILS)
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int): BaseResult<MovieDetail>
}