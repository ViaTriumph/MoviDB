package com.practice.movidb.common

import com.practice.movidb.network.common.BaseError
import com.practice.movidb.network.common.Result
import kotlinx.coroutines.runBlocking
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


class ResultCall<T : Any, U : Any>(
    private val delegate: Call<T>,
    private val errorConverter: Converter<ResponseBody, U>
) : Call<Result<T, U>> {

    override fun enqueue(callback: Callback<Result<T, U>>) = delegate.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            response.body()?.let { body ->

                //TODO move
                val responseError = response.errorBody()
                val error: U? = when {
                    responseError == null -> null
                    responseError.contentLength() == 0L -> null
                    else -> try {
                        errorConverter.convert(responseError)
                    } catch (e: Exception) {
                        null
                    }
                }

                //Check if response is from cache or network
                val type = if (response.raw().cacheResponse != null) {
                    Result.Type.CACHE
                } else {
                    Result.Type.API
                }

                val model = when (val code = response.code()) {
                    in 200..299 -> Result.Success<T>(code, body, type)
                    401 -> Result.SessionError<U>(error)
                    in 402..499 -> Result.ClientError<U>(code, error)
                    in 500..599 -> Result.ServerError<U>(code, error)
                    else -> Result.UnexpectedError<U>(code, error)
                }
                callback.onResponse(this@ResultCall, Response.success(model))
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            val model = if (t is IOException) {
                Result.NetworkError<U>(t)
            } else {
                Result.UnexpectedError<U>(-1, null)
            }
            callback.onResponse(this@ResultCall, Response.success(model))
        }

    })

    override fun clone(): Call<Result<T, U>> = ResultCall(delegate.clone(), errorConverter)

    override fun execute(): Response<Result<T, U>> {
        throw UnsupportedOperationException("ResponseCall does not support execute")
    }

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled() = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()

}

class ResultCallAdapter<T : Any, U : Any>(
    private val successType: Type,
    private val errorConverter: Converter<ResponseBody, U>
) : CallAdapter<T, Call<Result<T, U>>> {
    override fun responseType(): Type = successType //TODO why successtype

    override fun adapt(call: Call<T>): Call<Result<T, U>> = ResultCall(call, errorConverter)

}

class ResultCallAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {

        //suspend functions have return types wrapped in Call
        if (getRawType(returnType) != Call::class.java) {
            return null
        }

        check(returnType is ParameterizedType) {
            "Return type must be Call<ResponseModel<T>> or Call<ResponseModel<out T>>."
        }

        val responseType = getParameterUpperBound(0, returnType)
        //Response inside call should be ResponseModel
        if (getRawType(responseType) != Result::class.java) {
            return null
        }

        check(responseType is ParameterizedType) {
            "Return type must be ResponseModel<T> or ResponseModel<out T>."
        }

        val successBodyType = getParameterUpperBound(0, responseType)
        val errorBodyType = getParameterUpperBound(1, responseType)

        val errorBodyConverter =
            retrofit.nextResponseBodyConverter<Any>(null, errorBodyType, annotations)

        return ResultCallAdapter<Any, Any>(successBodyType, errorBodyConverter)
    }
}

interface HttpBinService {
    @GET("/ip")
    suspend fun getMyIp(): Result<Ip, BaseError>
}

class Ip {
    var origin: String? = null
}

fun main(args: Array<String>) = runBlocking{
    val retrofit = Retrofit.Builder()
        .baseUrl("http://httpbin.org")
        .addCallAdapterFactory(ResultCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(HttpBinService::class.java)

    val data = service.getMyIp()
    println(data.data?.origin)
}
typealias BaseResult<T> = Result<T, BaseError>
