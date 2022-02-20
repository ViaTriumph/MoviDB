package com.practice.movidb.network.common

import android.util.Log
import com.practice.movidb.common.CommonUtils
import com.practice.movidb.network.movie.model.PopularMovies
import com.practice.movidb.network.movie.service.MovieService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

object BaseNetwork {
    fun <S> createService(retrofit: Retrofit,clazz: Class<S>): S{
        return retrofit.create(clazz)
    }

    fun makeRequest(client: MovieService){
        val call : Call<PopularMovies> = client.getPopularMovies(
            CommonUtils.API_KEY,
            "en-GB",
            1,
            "GB"
        )

        call.enqueue(object : Callback<PopularMovies>{
            override fun onResponse(call: Call<PopularMovies>, response: Response<PopularMovies>) {
                if(response.isSuccessful){
                    Log.d("MOVIDB", "Response: ${response.body()}")
                }
            }

            override fun onFailure(call: Call<PopularMovies>, t: Throwable) {
                Log.d("MOVIDB", "Error")
            }

        })
    }
}