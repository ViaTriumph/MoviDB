package com.practice.movidb.shared.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [MovieEntity::class, PopularMoviesEntity::class, NowPlayingMovesEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(GenreListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun searchFtsDao(): SearchFtsDao
    abstract fun popularMoviesDao(): PopularMoviesDao
    abstract fun nowPlayingMoviesDao(): NowPlayingDao
    abstract fun movieDao(): MovieDao

    companion object {
        const val TAG = "app_data_base"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        TAG
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}