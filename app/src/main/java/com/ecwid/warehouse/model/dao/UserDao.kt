package com.ecwid.warehouse.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ecwid.warehouse.model.entity.Product

@Dao
interface UserDao {

    @Query("SELECT * FROM products")
    fun findAll(): LiveData<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(products: List<Product>)

    @Query("SELECT max(id) FROM products")
    fun getMaxId(): Long


    @Query("SELECT * FROM products Where id =:id")
    fun getProductById(id: Long?): LiveData<Product>
}