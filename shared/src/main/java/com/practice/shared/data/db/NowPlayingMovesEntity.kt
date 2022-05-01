package com.practice.shared.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "now_playing", indices = [Index(value = ["movie_id"], unique = true)])
data class NowPlayingMovesEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "row_id")
    val rowId: Int,

    @ColumnInfo(name = "movie_id")
    val movieId: Int?,

    @ColumnInfo(name = "time_stamp")
    val timeStamp: Long
)
