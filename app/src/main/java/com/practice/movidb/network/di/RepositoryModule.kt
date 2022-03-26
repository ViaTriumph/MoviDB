package com.practice.movidb.network.di

import com.practice.movidb.network.common.BaseNetwork
import com.practice.movidb.network.movie.domain.MovieRepository
import com.practice.movidb.network.movie.service.MovieService
import com.practice.movidb.network.search.data.SearchDataRepository
import com.practice.movidb.network.search.domain.SearchRepository
import com.practice.movidb.network.search.service.SearchService
import com.practice.movidb.shared.data.details.MovieDetailDataSource
import com.practice.movidb.shared.data.details.MovieDetailService
import com.practice.movidb.shared.data.details.MovieDetailsRepositoryImpl
import com.practice.movidb.shared.data.movie.MovieDataRepository
import com.practice.movidb.shared.data.movie.MovieDataSource
import com.practice.movidb.shared.data.search.SearchDataSource
import com.practice.movidb.shared.di.IODispatcher
import com.practice.movidb.shared.domain.details.MovieDetailsRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieService(retrofit: Retrofit): MovieService{
        return BaseNetwork.createService(retrofit, MovieService::class.java)
    }

    @Provides
    @Singleton
    fun providesSearchService(retrofit: Retrofit): SearchService {
        return BaseNetwork.createService(retrofit, SearchService::class.java)
    }

    @Provides
    @Singleton
    fun providesMovieDetailService(retrofit: Retrofit): MovieDetailService {
        return BaseNetwork.createService(retrofit, MovieDetailService::class.java)
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
        @IODispatcher coroutineDispatcher: CoroutineDispatcher,
        movieDetailDataSource: MovieDetailDataSource
    ): MovieDetailsRepository {
        return MovieDetailsRepositoryImpl(
            movieDetailService,
            movieDetailDataSource,
            coroutineDispatcher
        )
    }
}