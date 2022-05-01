package com.practice.shared.domain

import com.practice.shared.data.network.BaseError
import com.practice.shared.data.network.BaseResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import com.practice.shared.data.network.Result

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