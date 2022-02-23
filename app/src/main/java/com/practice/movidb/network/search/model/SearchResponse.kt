package com.practice.movidb.network.search.model

import com.practice.movidb.network.movie.domain.model.Result

data class SearchResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)
