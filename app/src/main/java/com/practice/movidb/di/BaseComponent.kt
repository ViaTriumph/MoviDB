package com.practice.movidb.di

import android.content.Context
import com.practice.movidb.activity.MainActivity
import com.practice.movidb.network.di.NetworkModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface BaseComponent {

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context): BaseComponent
    }

    fun inject(activity: MainActivity)
}