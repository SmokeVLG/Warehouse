package com.ecwid.warehouse.app

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.ecwid.warehouse.app.di.databaseModule
import com.ecwid.warehouse.app.di.repositoryModule
import com.ecwid.warehouse.app.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger(Level.DEBUG)
            modules(listOf(viewModelModule, repositoryModule, databaseModule))
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}