package com.practice.movidb.network.search.service

import com.practice.movidb.network.common.ApiEndpoints
import com.practice.movidb.network.search.model.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET(ApiEndpoints.SEARCH_MOVIE)
    fun searchMove(@Query("query") query: String): Call<SearchResponse>

}