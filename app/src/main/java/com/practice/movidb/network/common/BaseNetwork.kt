package com.practice.movidb.network.common

import android.util.Log
import com.practice.movidb.network.movie.domain.model.PopularMovies
import com.practice.movidb.network.movie.service.MovieService
import com.practice.movidb.network.search.model.SearchResponse
import com.practice.movidb.network.search.service.SearchService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

object BaseNetwork {
    fun <S> createService(retrofit: Retrofit,clazz: Class<S>): S{
        return retrofit.create(clazz)
    }
}