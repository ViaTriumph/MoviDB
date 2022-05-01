package com.practice.shared.di

import android.content.Context
import com.google.gson.Gson
import com.practice.shared.data.db.AppDatabase
import com.practice.shared.data.details.MovieDetailDataSource
import com.practice.shared.data.details.MovieDetailDataSourceImpl
import com.practice.shared.data.movie.MovieDataSource
import com.practice.shared.data.movie.MovieDataSourceImpl
import com.practice.shared.data.search.SearchDataSource
import com.practice.shared.data.search.SearchDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

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

    @Provides
    @Singleton
    fun providesMovieDetailDataSource(appDatabase: AppDatabase): MovieDetailDataSource {
        return MovieDetailDataSourceImpl(appDatabase)
    }
}