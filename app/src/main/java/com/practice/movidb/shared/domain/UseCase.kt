package com.practice.movidb.shared.domain

import com.practice.movidb.common.BaseResult
import com.practice.movidb.network.common.BaseError
import com.practice.movidb.network.common.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * Executes business logic using coroutines.
 * Reference: [this](https://github.com/google/iosched/blob/main/shared/src/main/java/com/google/samples/apps/iosched/shared/domain/CoroutineUseCase.kt)
 */
abstract class UseCase<in T, U : Any>(private val coroutinesDispatcher: CoroutineDispatcher) {

    suspend fun invoke(parameters: T): BaseResult<U> {
        return try {
            withContext(coroutinesDispatcher) {
                execute(parameters)
            }
        } catch (e: Exception) {
            //TODO log
            Result.Error(
                code = -1,
                body = BaseError(code = -1, message = e.toString()),
                mType = Result.Type.OTHER
            )
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: T): BaseResult<U>
}