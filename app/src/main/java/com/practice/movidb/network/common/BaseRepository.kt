package com.practice.movidb.network.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response

abstract class BaseRepository {

//    protected suspend fun <T> getResultFlow(call: suspend () -> Response<T>): Flow<ResponseModel<T>> {
//        return getResultFlow(call, ResponseModel.Type.API)
//    }
//
//    private suspend fun <T> getResultFlow(
//        call: suspend () -> Response<T>,
//        type: ResponseModel.Type = ResponseModel.Type.API
//    ): Flow<ResponseModel<T>> {
//        return flow {
//            emit(ResponseModel.Loading())
//            val response = call()
//            if (response.isSuccessful) {
//                emit(ResponseModel.Success(response.body(), response.code(), type))
//            } else {
//                emit(
//                    ResponseModel.Error(
//                        response.message(),
//                        response.errorBody(),
//                        response.code(),
//                        type
//                    )
//                )
//            }
//        }.catch { e ->
//            emit(ResponseModel.Error(e.toString(), null, -1, type))
//        }
//    }
}