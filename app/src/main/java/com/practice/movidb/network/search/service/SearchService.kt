package com.practice.movidb.network.search.service

import com.practice.movidb.common.BaseResult
import com.practice.movidb.network.common.ApiEndpoints
import com.practice.movidb.shared.data.movie.MovieList
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface SearchService {

    @GET(ApiEndpoints.SEARCH_MOVIE)
    suspend fun getSearchResult(
        @QueryMap(encoded = true) options: Map<String, String>
    ): BaseResult<MovieList>

}