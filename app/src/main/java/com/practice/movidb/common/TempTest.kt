package com.practice.movidb.common

import com.practice.movidb.network.common.BaseError
import com.practice.movidb.network.common.ResponseModel
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


class TestCallAdapter{

    internal class ResponseCall<T: Any, U: Any>(private val delegate: Call<T>,
                                                private val errorConverter: Converter<ResponseBody, U>): Call<ResponseModel<T, U>>{

        override fun enqueue(callback: Callback<ResponseModel<T, U>>) = delegate.enqueue(object: Callback<T>{
            override fun onResponse(call: Call<T>, response: Response<T>) {
                response.body()?.let { body ->

                    //TODO move
                    val responseError = response.errorBody()
                    val error: U? = when{
                        responseError == null -> null
                        responseError.contentLength() == 0L -> null
                        else -> try {
                            errorConverter.convert(responseError)
                        }catch (e: Exception) {
                            null
                        }
                    }

                    val model = when(val code = response.code()){
                        in 200..299 -> ResponseModel.Success<T>(code, body)
                        401 -> ResponseModel.SessionError<U>(error)
                        in 402..499 -> ResponseModel.ClientError<U>(code, error)
                        in 500..599 -> ResponseModel.ServerError<U>(code, error)
                        else -> ResponseModel.UnexpectedError<U>(code, error)
                    }
                    callback.onResponse(this@ResponseCall, Response.success(model))
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val model = if (t is IOException){
                    ResponseModel.NetworkError<U>(t)
                }else{
                    ResponseModel.UnexpectedError<U>(-1, null)
                }
                callback.onResponse(this@ResponseCall, Response.success(model))
            }

        })

        override fun clone(): Call<ResponseModel<T, U>> = ResponseCall(delegate.clone(), errorConverter)

        override fun execute(): Response<ResponseModel<T, U>>{
            throw UnsupportedOperationException("ResponseCall does not support execute")
        }

        override fun isExecuted(): Boolean = delegate.isExecuted

        override fun cancel() = delegate.cancel()

        override fun isCanceled() = delegate.isCanceled

        override fun request(): Request = delegate.request()

        override fun timeout(): Timeout = delegate.timeout()

    }

    internal class ResponseCallAdapter<T: Any, U: Any>(
        private val successType: Type,
        private val errorConverter: Converter<ResponseBody, U>
    ): CallAdapter<T, Call<ResponseModel<T, U>>>{
        override fun responseType(): Type = successType //TODO why successtype

        override fun adapt(call: Call<T>): Call<ResponseModel<T, U>> = ResponseCall(call, errorConverter)

    }

    internal class ResponseAdapterFactory: CallAdapter.Factory(){

        override fun get(
            returnType: Type,
            annotations: Array<out Annotation>,
            retrofit: Retrofit
        ): CallAdapter<*, *>? {

            //suspend functions have return types wrapped in Call
            if(getRawType(returnType) != Call::class.java){
                return null
            }

            check(returnType is ParameterizedType){
                "Return type must be Call<ResponseModel<T>> or Call<ResponseModel<out T>>."
            }

            val responseType = getParameterUpperBound(0,returnType)
            //Response inside call should be ResponseModel
            if(getRawType(responseType) != ResponseModel::class.java){
                return null
            }

            check(responseType is ParameterizedType){
                "Return type must be ResponseModel<T> or ResponseModel<out T>."
            }

            val successBodyType = getParameterUpperBound(0, responseType)
            val errorBodyType = getParameterUpperBound(1, responseType)

            val errorBodyConverter = retrofit.nextResponseBodyConverter<Any>(null, errorBodyType, annotations)

            return ResponseCallAdapter<Any, Any>(successBodyType, errorBodyConverter)
        }
    }
}

interface HttpBinService {
    @GET("/ip")
    suspend fun getMyIp(): ResponseModel<Ip, BaseError>
}

class Ip {
    var origin: String? = null
}

fun main(args: Array<String>) = runBlocking{
    val retrofit = Retrofit.Builder()
        .baseUrl("http://httpbin.org")
        .addCallAdapterFactory(TestCallAdapter.ResponseAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(HttpBinService::class.java)

    val data = service.getMyIp()
    println(data.data?.origin)
}
typealias Result<T> = ResponseModel<T, BaseError>
