package com.ecwid.warehouse.app.di

import android.app.Application
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.ecwid.warehouse.R

import com.ecwid.warehouse.model.AppDatabase
import com.ecwid.warehouse.model.dao.ProductDao
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

    fun provideDao(database: AppDatabase): ProductDao {
        return database.productDao
    }

    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
}

val repositoryModule = module {
    fun provideProductRepository(dao: ProductDao): ProductRepository {
        return ProductRepository(dao)
    }

    single { provideProductRepository(get()) }
}

