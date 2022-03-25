package com.practice.movidb.network.search.domain

import com.practice.movidb.common.BaseResult
import com.practice.movidb.shared.domain.movie.MovieList
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun getSearchResults(queryOptions: Map<String, String>): Flow<BaseResult<MovieList>>
}