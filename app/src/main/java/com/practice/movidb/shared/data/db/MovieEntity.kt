package com.practice.movidb.shared.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


//TODO add FTS4
@Entity(tableName = "movie_fts")
data class MovieEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowId")
    val rowId: Int, //TODO remove after fts4 addition

    @ColumnInfo(name = "adult")
    val adult: Boolean,

    @ColumnInfo(name = "genreIds")
    val genreIds: List<Int>,

    @ColumnInfo(name = "movieId")
    val id: Int,

    @ColumnInfo(name = "overview")
    val overview: String,

    @ColumnInfo(name = "posterPath")
    val posterPath: String,

    @ColumnInfo(name = "releaseDate")
    val releaseDate: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "voteAverage")
    val voteAverage: Double,

    @ColumnInfo(name = "voteCount")
    val voteCount: Int
)