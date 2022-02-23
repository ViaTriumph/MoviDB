package com.practice.movidb.network.di

import com.practice.movidb.common.CommonUtils
import com.practice.movidb.network.common.ApiEndpoints
import com.practice.movidb.network.common.BaseNetwork
import com.practice.movidb.network.movie.service.MovieService
import com.practice.movidb.network.search.service.SearchService
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
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
        return provideHttpInterceptor(builder).build()
    }

    private fun provideHttpInterceptor(okHttpClient: OkHttpClient.Builder): OkHttpClient.Builder{
        return okHttpClient.addInterceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url()
            val url = originalHttpUrl
                .newBuilder()
                .addQueryParameter("api_key", CommonUtils.API_KEY)
                .build()
            val requestBuilder = original
                .newBuilder()
                .url(url)

            val request = requestBuilder.build()
            chain.proceed(request)
        }
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



}