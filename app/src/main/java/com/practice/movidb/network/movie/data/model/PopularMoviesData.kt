package com.practice.movidb.network.movie.data.model

import com.practice.movidb.network.common.DataMapperUtil
import com.practice.movidb.network.movie.domain.model.Movie as DomainResult
import com.practice.movidb.network.movie.domain.model.PopularMovies as DomainPopularMovies

data class PopularMovies(
    val page: Int? = 0,
    val results: List<Result?>? = listOf(),
    val total_pages: Int? = 0,
    val total_results: Int? = 0
){
    fun convertToDomain(): DomainPopularMovies{
        return DomainPopularMovies(
            page = this.page ?: 0,
            results = convertResultsGroup(this.results),
            total_pages = this.total_pages ?: 0,
            total_results = this.total_results ?: 0,
        )
    }

    private fun convertResultsGroup(list: List<Result?>?): List<DomainResult>{
        return list?.mapNotNull { it?.convertToDomain() } ?: listOf()
    }
}

data class Result(
    val adult: Boolean? = false,
    val backdrop_path: String? = "",
    val genre_ids: List<Int?>? = listOf(),
    val id: Int? = 0,
    val original_language: String? = "",
    val original_title: String? = "",
    val overview: String? = "",
    val popularity: Double? = 0.0,
    val poster_path: String? = "",
    val release_date: String? = "",
    val title: String? = "",
    val video: Boolean? = false,
    val vote_average: Double? = 0.0,
    val vote_count: Int? = 0
){
    fun convertToDomain(): DomainResult{
        return DomainResult(
            adult = this.adult ?: false,
            backdrop_path = this.backdrop_path ?: "",
            genre_ids = DataMapperUtil.removeNullsFromList(this.genre_ids),
            id = this.id ?: 0,
            original_language = this.original_language ?: "",
            original_title = this.original_title ?: "",
            overview = this.overview ?: "",
            popularity = this.popularity ?: 0.0,
            poster_path = this.poster_path ?: "",
            release_date = this.release_date ?: "",
            title = this.title ?: "",
            video = this.video ?: false,
            vote_average = this.vote_average ?: 0.0,
            vote_count = this.vote_count ?: 0,
        )
    }
}