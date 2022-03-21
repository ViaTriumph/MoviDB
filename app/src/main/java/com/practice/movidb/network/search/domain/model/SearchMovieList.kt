package com.practice.movidb.network.search.domain.model

import com.practice.movidb.network.movie.domain.model.Movie

data class SearchMovieList(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)
