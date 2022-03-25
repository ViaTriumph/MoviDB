package com.practice.movidb.shared.data.movie

import com.google.gson.annotations.SerializedName
import com.practice.movidb.shared.common.DataMapperUtil
import com.practice.movidb.shared.domain.movie.Movie as DomainMovie
import com.practice.movidb.shared.domain.movie.MovieList as DomainMovieList

data class MovieList(
    val page: Int? = 0,
    val results: List<Movie?>? = listOf(),
    val total_pages: Int? = 0,
    val total_results: Int? = 0
) {
    fun convertToDomain(): DomainMovieList {
        return DomainMovieList(
            page = this.page ?: 0,
            results = convertResultsGroup(this.results),
            total_pages = this.total_pages ?: 0,
            total_results = this.total_results ?: 0,
        )
    }

    private fun convertResultsGroup(list: List<Movie?>?): List<DomainMovie> {
        return DataMapperUtil.convertToNonNull(list).map { it.convertToDomain() }
    }
}

data class Movie(
    @SerializedName("adult") val adult: Boolean? = false,
    @SerializedName("backdrop_path") val backdropPath: String? = "",
    @SerializedName("genre_ids") val genreIds: List<Int?>? = listOf(),
    @SerializedName("id") val id: Int? = 0,
    @SerializedName("original_language") val originalLanguage: String? = "",
    @SerializedName("original_title") val originalTitle: String? = "",
    @SerializedName("overview") val overview: String? = "",
    @SerializedName("popularity") val popularity: Double? = 0.0,
    @SerializedName("poster_path") val posterPath: String? = "",
    @SerializedName("release_date") val releaseDate: String? = "",
    @SerializedName("title") val title: String? = "",
    @SerializedName("video") val video: Boolean? = false,
    @SerializedName("vote_average") val voteAverage: Double? = 0.0,
    @SerializedName("vote_count") val voteCount: Int? = 0
) {
    fun convertToDomain(): DomainMovie {
        return DomainMovie(
            adult = this.adult ?: false,
            backdropPath = this.backdropPath ?: "",
            genreIds = DataMapperUtil.convertToNonNull(this.genreIds),
            id = this.id ?: 0,
            originalLanguage = this.originalLanguage ?: "",
            originalTitle = this.originalTitle ?: "",
            overview = this.overview ?: "",
            popularity = this.popularity ?: 0.0,
            posterPath = this.posterPath ?: "",
            releaseDate = this.releaseDate ?: "",
            title = this.title ?: "",
            video = this.video ?: false,
            voteAverage = this.voteAverage ?: 0.0,
            voteCount = this.voteCount ?: 0,
        )
    }
}