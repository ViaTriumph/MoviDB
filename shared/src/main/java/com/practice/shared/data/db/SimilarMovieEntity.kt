package com.practice.shared.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "similar_movie")
data class SimilarMovieEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "row_id")
    val rowId: Int,

    @ColumnInfo(name = "movie_id")
    val id: Int?,

    @ColumnInfo(name = "similar_movies")
    val similarMovies: IntList?,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long?
)