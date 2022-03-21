package com.practice.movidb.network.search.data.model

import com.practice.movidb.network.movie.data.model.Result
import com.practice.movidb.network.movie.domain.model.Movie
import com.practice.movidb.network.search.domain.model.SearchMovieList

data class SearchResponse(
    val page: Int? = 0,
    val results: List<Result>? = listOf(),
    val total_pages: Int? = 0,
    val total_results: Int? = 0
) {
    fun convertToDomain(): SearchMovieList {
        return SearchMovieList(
            page = this.page ?: 0,
            results = convertResultsGroup(this.results),
            total_pages = this.total_pages ?: 0,
            total_results = this.total_results ?: 0
        )
    }

    private fun convertResultsGroup(list: List<Result?>?): List<Movie> {
        return list?.mapNotNull { it?.convertToDomain() } ?: listOf()
    }
}