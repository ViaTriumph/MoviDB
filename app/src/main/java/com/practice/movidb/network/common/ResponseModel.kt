package com.practice.movidb.network.common


sealed class ResponseModel<out T>(
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
    ) : ResponseModel<T>(
        data = mData,
        httpCode = mHttpCode,
        type = mType
    )

    data class Error<out T>(
        val mMessage: String?,
        val mErrorData: Any? = null,
        val mHttpCode: Int,
        val mType: Type = Type.API
    ) : ResponseModel<T>(
        data = null,
        message = mMessage,
        httpCode = mHttpCode,
        type = mType,
        errorData = mErrorData
    )

    data class Loading<out T>(val mData: T? = null) : ResponseModel<T>(
        data = mData
    )

    data class None<out T>(val mData: T? = null) : ResponseModel<T>(
        data = mData
    )
}

fun <T,U> ResponseModel<T>.toDomain(mapper: Mapper<T,U>): ResponseModel<U>{
    return when(this){
        is ResponseModel.Success -> ResponseModel.Success(mapper.toDomain(data), httpCode, type)
        is ResponseModel.Error -> ResponseModel.Error(message, errorData, httpCode, type)
        is ResponseModel.Loading -> ResponseModel.Loading(mapper.toDomain(data))
        is ResponseModel.None -> ResponseModel.None(mapper.toDomain(data))
    }
}

interface Mapper<T,U>{
    fun toDomain(data: T?): U?
}