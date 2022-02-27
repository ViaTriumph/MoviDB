package com.practice.movidb.network.search.domain

import com.practice.movidb.network.common.ResponseModel
import com.practice.movidb.network.search.domain.model.SearchResponse
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun getSearchResults(query: String): Flow<ResponseModel<SearchResponse>>
}