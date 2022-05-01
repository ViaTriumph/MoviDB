package com.practice.shared.data.network

import com.google.gson.annotations.SerializedName

//TODO create custom base error
open class BaseError(
    @SerializedName("status_code") val code: Int? = 0,
    @SerializedName("status_message") val message: String? = "",
    @SerializedName("success") val success: Boolean? = false
)