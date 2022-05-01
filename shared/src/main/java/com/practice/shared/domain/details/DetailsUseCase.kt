package com.practice.shared.domain.details

import com.practice.shared.data.network.BaseResult
import com.practice.shared.di.IODispatcher
import com.practice.shared.domain.FlowUseCase
import com.practice.shared.domain.movie.MovieList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DetailsUseCase @Inject constructor(
    private val repository: MovieDetailsRepository,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher
) : FlowUseCase<Int, MovieDetail>(coroutineDispatcher) {
    override fun execute(parameters: Int): Flow<BaseResult<MovieDetail>> {
        return repository.getMovieDetail(parameters)
    }
}

class SimilarMoviesUseCase @Inject constructor(
    private val repository: MovieDetailsRepository,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : FlowUseCase<Int, MovieList>(dispatcher) {
    override fun execute(parameters: Int): Flow<BaseResult<MovieList>> {
        return repository.getSimilarMovies(parameters)
    }
}
