package com.practice.movidb.network.di

import com.practice.movidb.network.common.BaseNetwork
import com.practice.movidb.network.movie.data.MovieDataRepository
import com.practice.movidb.network.movie.domain.MovieRepository
import com.practice.movidb.network.movie.service.MovieService
import com.practice.movidb.network.search.data.SearchDataRepository
import com.practice.movidb.network.search.domain.SearchRepository
import com.practice.movidb.network.search.service.SearchService
import com.practice.movidb.shared.di.IODispatcher
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
    fun providesMovieRepositoryModule(movieService: MovieService): MovieRepository{
        return MovieDataRepository(movieService)
    }

    @Provides
    @Singleton
    fun providesSearchRepositoryModule(
        searchService: SearchService,
        @IODispatcher coroutineDispatcher: CoroutineDispatcher
    ): SearchRepository {
        return SearchDataRepository(searchService, coroutineDispatcher)
    }
}