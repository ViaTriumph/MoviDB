package com.practice.shared.data.search

import com.practice.shared.data.network.ApiEndpoints
import com.practice.shared.data.movie.MovieList
import com.practice.shared.data.network.BaseResult
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface SearchService {

    @GET(ApiEndpoints.SEARCH_MOVIE)
    suspend fun getSearchResult(
        @QueryMap(encoded = true) options: Map<String, String>
    ): BaseResult<MovieList>

}