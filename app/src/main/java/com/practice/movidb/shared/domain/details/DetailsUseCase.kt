package com.practice.movidb.shared.domain.details

import com.practice.movidb.common.BaseResult
import com.practice.movidb.shared.di.IODispatcher
import com.practice.movidb.shared.domain.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DetailsUseCase @Inject constructor(
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val repository: MovieDetailsRepository
) : FlowUseCase<Int, MovieDetail>(coroutineDispatcher) {
    override fun execute(parameters: Int): Flow<BaseResult<MovieDetail>> {
        return repository.getMovieDetail(parameters)
    }
}

