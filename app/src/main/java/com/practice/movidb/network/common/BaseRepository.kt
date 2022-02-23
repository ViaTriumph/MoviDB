package com.practice.movidb.network.common

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response

abstract class BaseRepository {

    protected suspend fun <T> getResultFlow(call: suspend () -> Response<T>): Flow<ResultApiModel<T>> {
        return getResultFlow(call, ResultApiModel.Type.API)
    }

    private suspend fun <T> getResultFlow(
        call: suspend () -> Response<T>,
        type: ResultApiModel.Type = ResultApiModel.Type.API
    ): Flow<ResultApiModel<T>> {
        return flow {
            emit(ResultApiModel.Loading())
            val response = call()
            if (response.isSuccessful) {
                emit(ResultApiModel.Success(response.body(), response.code(), type))
            } else {
                emit(
                    ResultApiModel.Error(
                        response.message(),
                        response.errorBody(),
                        response.code(),
                        type
                    )
                )
            }
        }.catch { e ->
            emit(ResultApiModel.Error(e.toString(), null, -1, type))
        }
    }
}