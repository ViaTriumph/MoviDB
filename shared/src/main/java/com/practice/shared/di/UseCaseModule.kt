package com.practice.shared.di

import com.practice.shared.domain.details.DetailsUseCase
import com.practice.shared.domain.details.MovieDetailsRepository
import com.practice.shared.domain.details.SimilarMoviesUseCase
import com.practice.shared.domain.explore.NowPlayingUseCase
import com.practice.shared.domain.explore.PopularMoviesUseCase
import com.practice.shared.domain.movie.MovieRepository
import com.practice.shared.domain.search.SearchRepository
import com.practice.shared.domain.search.SearchUseCase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Provides
    @Singleton
    fun providesDetailsUseCase(
        @IODispatcher coroutineDispatcher: CoroutineDispatcher,
        movieDetailsRepository: MovieDetailsRepository
    ): DetailsUseCase {
        return DetailsUseCase(movieDetailsRepository, coroutineDispatcher)
    }

    @Provides
    @Singleton
    fun providesPopularMoviesUseCase(
        @IODispatcher coroutineDispatcher: CoroutineDispatcher,
        movieRepository: MovieRepository
    ): PopularMoviesUseCase {
        return PopularMoviesUseCase(movieRepository, coroutineDispatcher)
    }

    @Provides
    @Singleton
    fun providesNowPlayingUseCase(
        @IODispatcher coroutineDispatcher: CoroutineDispatcher,
        movieRepository: MovieRepository
    ): NowPlayingUseCase {
        return NowPlayingUseCase(movieRepository, coroutineDispatcher)
    }

    @Provides
    @Singleton
    fun providesSimilarMoviesUseCase(
        @IODispatcher coroutineDispatcher: CoroutineDispatcher,
        movieDetailsRepository: MovieDetailsRepository
    ): SimilarMoviesUseCase {
        return SimilarMoviesUseCase(movieDetailsRepository, coroutineDispatcher)
    }

    @Provides
    @Singleton
    fun providesSearchUseCase(
        @IODispatcher coroutineDispatcher: CoroutineDispatcher,
        searchRepository: SearchRepository
    ): SearchUseCase {
        return SearchUseCase(searchRepository, coroutineDispatcher)
    }
}