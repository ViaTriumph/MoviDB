package com.practice.movidb.shared.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.practice.movidb.shared.data.movie.Movie


//TODO add FTS4
@Entity(tableName = "movie")
data class MovieEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "row_id")
    val rowId: Int, //TODO remove after fts4 addition

    @ColumnInfo(name = "adult")
    val adult: Boolean?,

    @ColumnInfo(name = "genre_ids")
    val genreIds: GenreList?,

    @ColumnInfo(name = "movie_id")
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

data class GenreList(val list: List<Int>)

/**
 * Type converter for List<Int> as Room does not take List<Int> as entry
 * //TODO
 */
class GenreListConverter {
    @TypeConverter
    fun stringToList(value: String): GenreList {
        return GenreList(value.split(",").map { it.toInt() })
    }

    @TypeConverter
    fun listToString(list: GenreList): String {
        return list.list.joinToString(",")
    }
}