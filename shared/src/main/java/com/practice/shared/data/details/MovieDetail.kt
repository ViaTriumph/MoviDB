package com.practice.shared.data.details

import com.google.gson.annotations.SerializedName
import com.practice.shared.domain.details.Genre as DomainGenre
import com.practice.shared.domain.details.MovieDetail as DomainMovieDetail

//Some entries removed as not used
data class MovieDetail(
    @SerializedName("adult") val adult: Boolean? = false,
    @SerializedName("backdrop_path") val backdropPath: String? = "",
    @SerializedName("budget") val budget: Int? = 0,
    @SerializedName("genres") val genres: List<Genre?>? = listOf(),
    @SerializedName("homepage") val homepage: String? = "",
    @SerializedName("id") val id: Int? = 0,
    @SerializedName("imdb_id") val imdbId: String? = "",
    @SerializedName("original_language") val originalLanguage: String? = "",
    @SerializedName("original_title") val originalTitle: String? = "",
    @SerializedName("overview") val overview: String? = "",
    @SerializedName("popularity") val popularity: Double? = 0.0,
    @SerializedName("poster_path") val posterPath: String? = "",
    @SerializedName("release_date") val releaseDate: String? = "",
    @SerializedName("revenue") val revenue: Int? = 0,
    @SerializedName("runtime") val runtime: Int? = 0,
    @SerializedName("status") val status: String? = "",
    @SerializedName("tagline") val tagline: String? = "",
    @SerializedName("title") val title: String? = "",
    @SerializedName("video") val video: Boolean? = false,
    @SerializedName("vote_average") val voteAverage: Double? = 0.0,
    @SerializedName("vote_count") val voteCount: Int? = 0
) {
    fun convertToDomainModel(): DomainMovieDetail {
        return DomainMovieDetail(
            adult = this.adult ?: false,
            backdropPath = this.backdropPath ?: "",
            budget = this.budget ?: 0,
            genres = com.practice.shared.common.DataMapperUtil.convertToNonNull(this.genres).map { convertToDomainGenre(it) },
            homepage = this.homepage ?: "",
            id = this.id ?: 0,
            imdbId = this.imdbId ?: "",
            originalLanguage = this.originalLanguage ?: "",
            originalTitle = this.originalTitle ?: "",
            overview = this.overview ?: "",
            popularity = this.popularity ?: 0.0,
            posterPath = this.posterPath ?: "",
            releaseDate = this.releaseDate ?: "",
            revenue = this.revenue ?: 0,
            runtime = this.runtime ?: 0,
            status = this.status ?: "",
            tagline = this.tagline ?: "",
            title = this.title ?: "",
            video = this.video ?: false,
            voteAverage = this.voteAverage ?: 0.0,
            voteCount = this.voteCount ?: 0,
        )
    }

    private fun convertToDomainGenre(genre: Genre): DomainGenre {
        return DomainGenre(
            id = genre.id ?: 0,
            name = genre.name ?: ""
        )
    }
}

data class Genre(
    @SerializedName("id") val id: Int? = 0,
    @SerializedName("name") val name: String? = ""
)

