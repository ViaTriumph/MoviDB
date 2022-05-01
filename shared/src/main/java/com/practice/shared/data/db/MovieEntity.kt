package com.practice.shared.data.db

import androidx.room.*
import com.practice.shared.data.movie.Movie

@Entity(tableName = "movie", indices = [Index(value = ["id"], unique = true)])
data class MovieEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "row_id")
    val rowId: Int,

    @ColumnInfo(name = "adult")
    val adult: Boolean?,

    @ColumnInfo(name = "genre_ids")
    val genreIds: IntList?,

    @ColumnInfo(name = "id")
    val id: Int?,

    @ColumnInfo(name = "overview")
    val overview: String?,

    @ColumnInfo(name = "poster_path")
    val posterPath: String?,

    @ColumnInfo(name = "release_date")
    val releaseDate: String?,

    @ColumnInfo(name = "title")
    val title: String?,

    @ColumnInfo(name = "vote_average")
    val voteAverage: Double?,

    @ColumnInfo(name = "vote_count")
    val voteCount: Int?,

    @ColumnInfo(name = "popularity")
    val popularity: Double?,

    @ColumnInfo(name = "time_stamp")
    val timeStamp: Long
) {
    fun convertToDataModel(): Movie {
        return Movie(
            adult = this.adult,
            genreIds = this.genreIds?.list,
            id = this.id,
            overview = this.overview,
            posterPath = this.posterPath,
            releaseDate = this.releaseDate,
            title = this.title,
            voteAverage = this.voteAverage,
            voteCount = this.voteCount,
            backdropPath = "",
            originalLanguage = "",
            originalTitle = "",
            popularity = 0.0,
            video = false,
        )
    }
}

data class IntList(val list: List<Int>)

/**
 * Type converter for List<Int> as Room does not take List<Int> as entry
 * //TODO
 */
class GenreListConverter {
    @TypeConverter
    fun stringToList(value: String): IntList {
        return try {
            IntList(value.split(",").map { it.toInt() })
        } catch (e: NumberFormatException) {
            IntList(emptyList())
        }
    }

    @TypeConverter
    fun listToString(list: IntList): String {
        return list.list.joinToString(",")
    }
}