package com.practice.movidb.network.common

import java.io.IOException

sealed class Result<out T : Any, out U : Any>( //Todo explain why Any was used (Hint: To use Nothing, Any is parent of Nothing)
    val data: T? = null,
    val httpCode: Int = 0,
    val errorData: U? = null,
    val type: Type = Type.API
) {
    enum class Type {
        API,
        CACHE,
        OTHER
    }

    data class Success<T : Any>(
        val mHttpCode: Int,
        val mData: T?,
        val mType: Type = Type.API
    ) : Result<T, Nothing>(
        data = mData,
        httpCode = mHttpCode,
        type = mType
    )

    open class Error<U : Any>(
        code: Int,
        body: U?,
        mType: Type = Type.API
    ) : Result<Nothing, U>(
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

    class Loading : Result<Nothing, Nothing>()

    class None : Result<Nothing, Nothing>()
}

fun <T : Any, U : Any, E : Any> Result<T, E>.toDomain(mapper: Mapper<T, U>): Result<U, E> {
    return when (this) {
        is Result.Success<T> -> Result.Success(httpCode, mapper.toDomain(data), type)
        is Result.Error<E> -> Result.Error(httpCode, errorData, type)
        is Result.Loading -> Result.Loading()
        is Result.None -> Result.None()
    }
}

interface Mapper<T,U>{
    fun toDomain(data: T?): U?
}