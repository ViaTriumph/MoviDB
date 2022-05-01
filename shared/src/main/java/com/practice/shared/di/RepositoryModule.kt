package com.practice.shared.di

import com.practice.shared.data.details.MovieDetailDataSource
import com.practice.shared.data.details.MovieDetailService
import com.practice.shared.data.details.MovieDetailsRepositoryImpl
import com.practice.shared.data.movie.MovieDataRepository
import com.practice.shared.data.movie.MovieDataSource
import com.practice.shared.data.movie.MovieService
import com.practice.shared.data.search.SearchDataRepository
import com.practice.shared.data.search.SearchDataSource
import com.practice.shared.data.search.SearchService
import com.practice.shared.domain.details.MovieDetailsRepository
import com.practice.shared.domain.movie.MovieRepository
import com.practice.shared.domain.search.SearchRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieService(retrofit: Retrofit): MovieService {
        return retrofit.create(MovieService::class.java)
    }

    @Provides
    @Singleton
    fun providesSearchService(retrofit: Retrofit): SearchService {
        return retrofit.create(SearchService::class.java)
    }

    @Provides
    @Singleton
    fun providesMovieDetailService(retrofit: Retrofit): MovieDetailService {
        return retrofit.create(MovieDetailService::class.java)
    }

    @Provides
    @Singleton
    fun providesMovieRepository(
        movieService: MovieService,
        @IODispatcher coroutineDispatcher: CoroutineDispatcher,
        movieDataSource: MovieDataSource
    ): MovieRepository {
        return MovieDataRepository(movieService, coroutineDispatcher, movieDataSource)
    }

    @Provides
    @Singleton
    fun providesSearchRepository(
        searchService: SearchService,
        @IODispatcher coroutineDispatcher: CoroutineDispatcher,
        searchDataSource: SearchDataSource
    ): SearchRepository {
        return SearchDataRepository(searchService, coroutineDispatcher, searchDataSource)
    }


    @Provides
    @Singleton
    fun providesMovieDetailsRepository(
        movieDetailService: MovieDetailService,
        movieDetailDataSource: MovieDetailDataSource,
        movieDataSource: MovieDataSource,
        @IODispatcher coroutineDispatcher: CoroutineDispatcher
    ): MovieDetailsRepository {
        return MovieDetailsRepositoryImpl(
            movieDetailService,
            movieDetailDataSource,
            movieDataSource,
            coroutineDispatcher
        )
    }
}