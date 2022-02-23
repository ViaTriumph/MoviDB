package com.practice.movidb.network.search.service

import com.practice.movidb.network.common.ApiEndpoints
import com.practice.movidb.network.search.data.model.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET(ApiEndpoints.SEARCH_MOVIE)
    suspend fun getSearchResult(@Query("query") query: String): Response<SearchResponse>

}