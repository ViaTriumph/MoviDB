package com.practice.shared.data.details

import com.practice.shared.data.movie.MovieList
import com.practice.shared.data.network.ApiEndpoints
import com.practice.shared.data.network.BaseResult
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDetailService {

    @GET(ApiEndpoints.MOVIE_DETAILS)
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int): BaseResult<MovieDetail>

    @GET(ApiEndpoints.SIMILAR_MOVIES)
    suspend fun getSimilarMovies(@Path("movie_id") movieId: Int): BaseResult<MovieList>
}