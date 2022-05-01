package com.practice.shared.data.network

abstract class BaseRepository {

    suspend fun <T : Any> getResult(
        call: suspend () -> BaseResult<T>
    ): BaseResult<T> {
        return call()
    }
}