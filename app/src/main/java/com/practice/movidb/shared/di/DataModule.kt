package com.practice.movidb.shared.di

import android.content.Context
import com.practice.movidb.shared.data.db.AppDatabase
import com.practice.movidb.shared.data.movie.MovieDataSource
import com.practice.movidb.shared.data.movie.MovieDataSourceImpl
import com.practice.movidb.shared.data.search.datasource.SearchDataSource
import com.practice.movidb.shared.data.search.datasource.SearchDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return AppDatabase.createDb(context)
    }

    @Provides
    @Singleton
    fun providesSearchDataSource(appDatabase: AppDatabase): SearchDataSource {
        return SearchDataSourceImpl(appDatabase)
    }

    @Provides
    @Singleton
    fun providesMovieDataSource(appDatabase: AppDatabase): MovieDataSource {
        return MovieDataSourceImpl(appDatabase)
    }
}