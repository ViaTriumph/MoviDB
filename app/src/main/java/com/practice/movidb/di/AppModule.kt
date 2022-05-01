package com.practice.movidb.di

import android.content.Context
import com.google.gson.Gson
import com.practice.shared.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule{

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context, gson: Gson): AppDatabase {
        return AppDatabase.createDb(context, gson)
    }

}