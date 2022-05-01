package com.practice.movidb

import android.app.Application
import com.practice.movidb.di.BaseComponent
import com.practice.movidb.di.DaggerBaseComponent
import com.practice.shared.di.*

open class ShowApplication : Application() {

    val baseComponent: BaseComponent by lazy {
        DaggerBaseComponent.factory().create(applicationContext)
    }
}