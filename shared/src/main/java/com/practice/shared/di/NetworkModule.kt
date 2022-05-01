package com.practice.shared.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.practice.movidb.common.ResultCallAdapterFactory
import com.practice.shared.common.CommonUtils
import dagger.Module
import dagger.Provides
import okhttp3.Cache
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
        context: Context,
        builder: OkHttpClient.Builder,
        httpLoggingInterceptor: HttpLoggingInterceptor,
//        chuckerInterceptor: ChuckerInterceptor
    ): OkHttpClient {
        return provideHttpInterceptor(builder)
            .addInterceptor(httpLoggingInterceptor)
//            .addInterceptor(chuckerInterceptor)
            .cache(provideCache(context))
            .build()
    }

    private fun provideCache(context: Context): Cache {
        return Cache(context.cacheDir, provideCacheSize())
    }

    private fun provideCacheSize() = (20 * 1024 * 1024).toLong() //20MB

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
            .baseUrl(com.practice.shared.data.network.ApiEndpoints.BASE_URL)
            .client(client)
            .addCallAdapterFactory(callAdapter)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

//    @Provides
//    @Singleton
//    fun provideChuckerInterceptor(context: Context): ChuckerInterceptor {
//        val chuckerCollector = ChuckerCollector(
//            context = context,
//            showNotification = true,
//            retentionPeriod = RetentionManager.Period.ONE_HOUR
//        )
//
//        return ChuckerInterceptor.Builder(context)
//            .collector(chuckerCollector)
//            .redactHeaders("Auth-Token", "Bearer", "User-Session")
//            .alwaysReadResponseBody(true)
//            .build()
//    }

}