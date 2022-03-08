package com.practice.movidb.network.common

import com.google.gson.annotations.SerializedName

open class BaseError(
    @SerializedName("status_code") val code: Int? = 0,
    @SerializedName("status_message") val message: String? = "",
    @SerializedName("success") val success: Boolean? = false
)

class NetworkError(message: String): BaseError(0, message)
class UnexpectedError(code: Int, message: String): BaseError(code, message)
class ClientError(code: Int, message: String): BaseError(code, message)
class ServerError(code: Int, message: String): BaseError(code, message)
class SessionError(message: String): BaseError(401, message)