package com.practice.shared.domain.search

import com.practice.shared.data.network.BaseResult
import com.practice.shared.domain.movie.MovieList
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun getSearchResults(queryOptions: Map<String, String>): Flow<BaseResult<MovieList>>
}