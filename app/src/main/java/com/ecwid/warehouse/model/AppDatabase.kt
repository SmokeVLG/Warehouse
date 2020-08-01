package com.ecwid.warehouse.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ecwid.warehouse.model.dao.ProductDao
import com.ecwid.warehouse.model.entity.Product

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val productDao: ProductDao
}