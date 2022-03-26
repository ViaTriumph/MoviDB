package com.practice.movidb.di

import android.content.Context
import com.practice.movidb.activity.MainActivity
import com.practice.movidb.network.di.NetworkModule
import com.practice.movidb.network.di.RepositoryModule
import com.practice.movidb.shared.di.DataModule
import com.practice.movidb.shared.di.DispatcherModule
import com.practice.movidb.shared.di.UseCaseModule
import com.practice.movidb.ui.detail.MovieDetailFragment
import com.practice.movidb.ui.explore.ExploreFragment
import com.practice.movidb.ui.search.SearchFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, RepositoryModule::class, DispatcherModule::class, AppModule::class, DataModule::class, UseCaseModule::class])
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