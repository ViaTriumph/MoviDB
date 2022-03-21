package com.practice.movidb.network.di

import com.practice.movidb.common.CommonUtils
import com.practice.movidb.common.ResultCallAdapterFactory
import com.practice.movidb.network.common.ApiEndpoints
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton // TODO change it to custom scope
    fun providesOkHttpBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        builder: OkHttpClient.Builder,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return provideHttpInterceptor(builder)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    private fun provideHttpInterceptor(okHttpClient: OkHttpClient.Builder): OkHttpClient.Builder {
        return okHttpClient.addInterceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url
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
    fun providesGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideResultAdapterFactory(): ResultCallAdapterFactory {
        return ResultCallAdapterFactory()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        callAdapter: ResultCallAdapterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiEndpoints.BASE_URL)
            .client(client)
            .addCallAdapterFactory(callAdapter)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }


}