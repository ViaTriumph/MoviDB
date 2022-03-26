package com.practice.movidb.shared.data.db

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.practice.movidb.shared.data.details.Genre
import com.practice.movidb.shared.data.details.MovieDetail

@Entity(tableName = "movie_detail")
data class MovieDetailEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "row_id")
    val rowId: Int,

    @ColumnInfo(name = "adult")
    val adult: Boolean? = false,

    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String? = "",

    @ColumnInfo(name = "budget")
    val budget: Int? = 0,

    @ColumnInfo(name = "genres")
    val genres: List<Genre?>? = listOf(),

    @ColumnInfo(name = "homepage")
    val homepage: String? = "",

    @ColumnInfo(name = "id")
    val id: Int? = 0,

    @ColumnInfo(name = "imdb_id")
    val imdbId: String? = "",

    @ColumnInfo(name = "original_language")
    val originalLanguage: String? = "",

    @ColumnInfo(name = "original_title")
    val originalTitle: String? = "",

    @ColumnInfo(name = "overview")
    val overview: String? = "",

    @ColumnInfo(name = "popularity")
    val popularity: Double? = 0.0,

    @ColumnInfo(name = "poster_path")
    val posterPath: String? = "",

    @ColumnInfo(name = "release_date")
    val releaseDate: String? = "",

    @ColumnInfo(name = "revenue")
    val revenue: Int? = 0,

    @ColumnInfo(name = "runtime")
    val runtime: Int? = 0,

    @ColumnInfo(name = "status")
    val status: String? = "",

    @ColumnInfo(name = "tagline")
    val tagline: String? = "",

    @ColumnInfo(name = "title")
    val title: String? = "",

    @ColumnInfo(name = "video")
    val video: Boolean? = false,

    @ColumnInfo(name = "vote_average")
    val voteAverage: Double? = 0.0,

    @ColumnInfo(name = "vote_count")
    val voteCount: Int? = 0
) {
    fun convertToDataModel(): MovieDetail {
        return MovieDetail(
            adult = this.adult,
            backdropPath = this.backdropPath,
            budget = this.budget,
            genres = this.genres,
            homepage = this.homepage,
            id = this.id,
            imdbId = this.imdbId,
            originalLanguage = this.originalLanguage,
            originalTitle = this.originalTitle,
            overview = this.overview,
            popularity = this.popularity,
            posterPath = this.posterPath,
            releaseDate = this.releaseDate,
            revenue = this.revenue,
            runtime = this.runtime,
            status = this.status,
            tagline = this.tagline,
            title = this.title,
            video = this.video,
            voteAverage = this.voteAverage,
            voteCount = this.voteCount,
        )
    }
}

//Using gson to convert to string as to avoid complex nesting classes in tables
@ProvidedTypeConverter
class GenreConverter(private val gson: Gson) {

    @TypeConverter
    fun stringToGenre(value: String): List<Genre?>? {
        val type = object : TypeToken<List<Genre?>?>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun genreToString(genres: List<Genre?>?): String {
        return gson.toJson(genres)
    }
}



