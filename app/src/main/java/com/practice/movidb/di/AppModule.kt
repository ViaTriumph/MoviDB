package com.practice.movidb.di

import android.content.Context
import com.practice.movidb.shared.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module()
interface AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }
}