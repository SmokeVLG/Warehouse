package com.ecwid.warehouse.app.di

import android.app.Application
import androidx.room.Room

import com.ecwid.warehouse.model.AppDatabase
import com.ecwid.warehouse.model.dao.UserDao
import com.ecwid.warehouse.model.repository.ProductRepository
import com.ecwid.warehouse.viewmodel.AddProductViewModel
import com.ecwid.warehouse.viewmodel.ProductDetailViewModel
import com.ecwid.warehouse.viewmodel.ProductViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module



val viewModelModule = module {
    viewModel { ProductViewModel(get()) }
    viewModel { AddProductViewModel(get()) }
    viewModel { ProductDetailViewModel(get()) }
}

val databaseModule = module {

    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "eds.database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    fun provideDao(database: AppDatabase): UserDao {
        return database.userDao
    }

    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
}

val repositoryModule = module {
    fun provideUserRepository(dao: UserDao): ProductRepository {
        return ProductRepository(dao)
    }

    single { provideUserRepository(get()) }
}

