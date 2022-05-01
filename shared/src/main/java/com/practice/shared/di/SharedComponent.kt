package com.practice.shared.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Scope

//@SharedScope
//@Component(
//    modules = [
//        DataModule::class,
//        DispatcherModule::class,
//        NetworkModule::class,
//        RepositoryModule::class,
//        UseCaseModule::class
//    ]
//)
//interface SharedComponent{
//    @Component.Factory
//    interface Factory{
//        fun create(@BindsInstance context: Context): SharedComponent
//    }
//}
//
//@Scope
//@Retention(AnnotationRetention.RUNTIME)
//annotation class SharedScope