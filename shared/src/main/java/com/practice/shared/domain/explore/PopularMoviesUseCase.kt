package com.practice.shared.domain.explore

import com.practice.shared.data.network.BaseResult
import com.practice.shared.di.IODispatcher
import com.practice.shared.domain.FlowUseCase
import com.practice.shared.domain.movie.MovieList
import com.practice.shared.domain.movie.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PopularMoviesUseCase @Inject constructor(
    private val repository: MovieRepository,
    @IODispatcher coroutineDispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, MovieList>(coroutineDispatcher) { //TODO flow use case??
    override fun execute(parameters: Unit): Flow<BaseResult<MovieList>> {
        return repository.getPopularMovies()
    }
}

class NowPlayingUseCase @Inject constructor(
    private val repository: MovieRepository,
    @IODispatcher coroutineDispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, MovieList>(coroutineDispatcher) { //TODO flow use case??
    override fun execute(parameters: Unit): Flow<BaseResult<MovieList>> {
        return repository.getNowPlayingMovies()
    }
}