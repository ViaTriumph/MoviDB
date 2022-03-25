package com.practice.movidb.shared.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [MovieEntity::class, PopularMoviesEntity::class, NowPlayingMovesEntity::class, MovieFtsEntity::class],
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

        //TODO if db is injected into graph with single instance , why volatile?

        fun createDb(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                TAG
            )
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        db.execSQL("INSERT INTO movie_fts(movie_fts) VALUES ('rebuild')") //TODO what does this exactly do?
                    }
                })
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}