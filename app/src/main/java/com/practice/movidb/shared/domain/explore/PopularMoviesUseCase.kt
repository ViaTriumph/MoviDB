package com.practice.movidb.shared.domain.explore

import com.practice.movidb.common.BaseResult
import com.practice.movidb.network.movie.domain.MovieRepository
import com.practice.movidb.network.movie.domain.model.MovieList
import com.practice.movidb.shared.di.IODispatcher
import com.practice.movidb.shared.domain.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class PopularMoviesUseCase(
    private val repository: MovieRepository,
    @IODispatcher coroutineDispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, MovieList>(coroutineDispatcher) { //TODO flow use case??
    override fun execute(parameters: Unit): Flow<BaseResult<MovieList>> {
        return repository.getPopularMovies()
    }
}

class NowPlayingUseCase(
    private val repository: MovieRepository,
    @IODispatcher coroutineDispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, MovieList>(coroutineDispatcher) { //TODO flow use case??
    override fun execute(parameters: Unit): Flow<BaseResult<MovieList>> {
        return repository.getNowPlayingMovies()
    }
}