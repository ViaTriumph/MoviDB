package com.practice.movidb.network.common

import com.practice.movidb.common.BaseResult

abstract class BaseRepository {

    protected suspend fun <T : Any> getResult(call: suspend () -> BaseResult<T>): BaseResult<T> {
        return getResult(call, Result.Type.API)
    }

    private suspend fun <T : Any> getResult(
        call: suspend () -> BaseResult<T>,
        type: Result.Type = Result.Type.API
    ): BaseResult<T> {
        return call()
    }
}