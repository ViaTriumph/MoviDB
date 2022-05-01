package com.practice.movidb.di

import android.content.Context
import com.practice.movidb.activity.MainActivity
import com.practice.movidb.ui.detail.MovieDetailFragment
import com.practice.movidb.ui.explore.ExploreFragment
import com.practice.movidb.ui.search.SearchFragment
import com.practice.shared.di.*
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Scope
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DataModule::class,
        DispatcherModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        UseCaseModule::class
    ]
)
interface BaseComponent {

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context): BaseComponent
    }

    fun inject(activity: MainActivity)
    fun inject(frag: SearchFragment)
    fun inject(frag: ExploreFragment)
    fun inject(frag: MovieDetailFragment)
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class BaseScope