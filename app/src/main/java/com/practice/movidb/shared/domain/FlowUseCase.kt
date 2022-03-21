package com.practice.movidb.shared.domain

import com.practice.movidb.common.BaseResult
import com.practice.movidb.network.common.BaseError
import com.practice.movidb.network.common.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

/**
 * Executes business logic and emits result as [Result<U>]
 */
abstract class FlowUseCase<in T : Any, U : Any>(private val coroutineDispatcher: CoroutineDispatcher) {
    operator fun invoke(parameters: T): Flow<BaseResult<U>> = execute(parameters)
        .catch { e -> emit(Result.Error(code = -1, body = BaseError(-1, e.toString()))) }
        .flowOn(coroutineDispatcher)

    protected abstract fun execute(parameters: T): Flow<BaseResult<U>>
}