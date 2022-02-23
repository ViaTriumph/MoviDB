package com.practice.movidb.network.common


sealed class ResultApiModel<out T>(
    val data: T?,
    val message: String? = null,
    val httpCode: Int = 0,
    val errorData: Any? = null,
    val type: Type = Type.API
) {
    enum class Type {
        API,
        CACHE
    }

    data class Success<out T>(
        val mData: T?,
        val mHttpCode: Int,
        val mType: Type = Type.API
    ) : ResultApiModel<T>(
        data = mData,
        httpCode = mHttpCode,
        type = mType
    )

    data class Error<out T>(
        val mMessage: String?,
        val mErrorData: Any? = null,
        val mHttpCode: Int,
        val mType: Type = Type.API
    ) : ResultApiModel<T>(
        data = null,
        message = mMessage,
        httpCode = mHttpCode,
        type = mType,
        errorData = mErrorData
    )

    data class Loading<out T>(val mData: T? = null) : ResultApiModel<T>(
        data = mData
    )

    data class None<out T>(val mData: T? = null) : ResultApiModel<T>(
        data = mData
    )
}

fun <T,U> ResultApiModel<T>.toDomain(mapper: Mapper<T,U>): ResultApiModel<U>{
    return when(this){
        is ResultApiModel.Success -> ResultApiModel.Success(mapper.toDomain(data), httpCode, type)
        is ResultApiModel.Error -> ResultApiModel.Error(message, errorData, httpCode, type)
        is ResultApiModel.Loading -> ResultApiModel.Loading(mapper.toDomain(data))
        is ResultApiModel.None -> ResultApiModel.None(mapper.toDomain(data))
    }
}

interface Mapper<T,U>{
    fun toDomain(data: T?): U?
}