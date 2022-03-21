package com.practice.movidb.network.common

import com.practice.movidb.common.BaseResult

abstract class BaseRepository {

    protected suspend fun <T : Any> getResultFlow(call: suspend () -> BaseResult<T>): BaseResult<T> {
        return getResultFlow(call, Result.Type.API)
    }

    private suspend fun <T : Any> getResultFlow(
        call: suspend () -> BaseResult<T>,
        type: Result.Type = Result.Type.API
    ): BaseResult<T> {
        return try {
            val response = call()
            response
        } catch (e: Exception) {
            Result.Error(code = -1, BaseError(code = -1, message = e.toString()), type)
        }
    }
}