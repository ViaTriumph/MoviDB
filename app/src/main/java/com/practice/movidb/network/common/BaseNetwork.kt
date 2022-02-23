package com.practice.movidb.network.common

import retrofit2.Retrofit

object BaseNetwork {
    fun <S> createService(retrofit: Retrofit,clazz: Class<S>): S{
        return retrofit.create(clazz)
    }
}