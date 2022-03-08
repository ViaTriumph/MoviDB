package com.practice.movidb.network.common

import java.io.IOException

sealed class ResponseModel<out T: Any, out U: Any>( //Todo explain why Any was used (Hint: To use Nothing, Any is parent of Nothing)
    val data: T? = null,
    val httpCode: Int = 0,
    val errorData: U? = null,
    val type: Type = Type.API
) {
    enum class Type {
        API,
        CACHE
    }

    data class Success<T: Any>(
        val mHttpCode: Int,
        val mData: T?,
        val mType: Type = Type.API
    ) : ResponseModel<T, Nothing>(
        data = mData,
        httpCode = mHttpCode,
        type = mType
    )

    open class Error<U: Any>(
        code: Int,
        body: U?,
        mType: Type = Type.API
    ) : ResponseModel<Nothing, U>(
        httpCode = code,
        type = mType,
        errorData = body
    )

    data class ApiError<U: Any>(val mCode: Int, val mBody: U?): Error<U>(mCode, mBody)

    data class NetworkError<U: Any>(val error: IOException?): Error<U>(0, null)

    data class ClientError<U: Any>(val mCode: Int, val mBody: U?): Error<U>(mCode, mBody)

    data class ServerError<U: Any>(val mCode: Int, val mBody: U?): Error<U>(mCode, mBody)

    data class UnexpectedError<U: Any>(val mCode: Int,val mBody: U?): Error<U>(mCode, mBody)

    data class SessionError<U: Any>(val mBody: U?): Error<U>(401, mBody)

    class Loading: ResponseModel<Nothing, Nothing>()

    class None: ResponseModel<Nothing, Nothing>()
}

fun <T: Any,U: Any,E: Any> ResponseModel<T, E>.toDomain(mapper: Mapper<T,U>): ResponseModel<U, E>{
    return when(this){
        is ResponseModel.Success<T> -> ResponseModel.Success(httpCode, mapper.toDomain(data), type)
        is ResponseModel.Error<E> -> ResponseModel.Error(httpCode, errorData, type)
        is ResponseModel.Loading -> ResponseModel.Loading()
        is ResponseModel.None -> ResponseModel.None()
    }
}

interface Mapper<T,U>{
    fun toDomain(data: T?): U?
}