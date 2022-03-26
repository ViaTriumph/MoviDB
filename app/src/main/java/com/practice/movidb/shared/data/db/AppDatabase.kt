package com.practice.movidb.shared.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson

@Database(
    entities = [MovieEntity::class,
        PopularMoviesEntity::class,
        NowPlayingMovesEntity::class,
        MovieFtsEntity::class,
        SimilarMovieEntity::class,
        MovieDetailEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(value = [GenreListConverter::class, GenreConverter::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun searchFtsDao(): SearchFtsDao
    abstract fun popularMoviesDao(): PopularMoviesDao
    abstract fun nowPlayingMoviesDao(): NowPlayingDao
    abstract fun movieDao(): MovieDao
    abstract fun movieDetailDao(): MovieDetailDao

    companion object {
        const val TAG = "app_data_base"

        fun createDb(context: Context, gson: Gson): AppDatabase {
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
                .addTypeConverter(GenreConverter(gson))
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}