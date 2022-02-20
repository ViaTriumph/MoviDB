package com.practice.movidb.network.di

import com.practice.movidb.network.common.ApiEndpoints
import com.practice.movidb.network.common.BaseNetwork
import com.practice.movidb.network.movie.service.MovieService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule{

    @Provides
    @Singleton // TODO change it to custom scope
    fun providesOkHttpBuilder(): OkHttpClient.Builder{
        return OkHttpClient.Builder()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(builder: OkHttpClient.Builder): OkHttpClient{
        return builder.build()
    }

    @Provides
    @Singleton
    fun providesGsonConverterFactory(): GsonConverterFactory{
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, gsonConverterFactory: GsonConverterFactory): Retrofit{
        return Retrofit.Builder()
            .baseUrl(ApiEndpoints.BASE_URL)
            .client(client)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieService(retrofit: Retrofit): MovieService{
        return BaseNetwork.createService(retrofit, MovieService::class.java)
    }

}