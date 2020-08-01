package com.ecwid.warehouse.model.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.ecwid.warehouse.model.dao.ProductDao
import com.ecwid.warehouse.model.entity.Product

class ProductRepository(private val productDao: ProductDao) {

    val data = productDao.findAll()

    suspend fun addOrUpdate(product: Product) {
        withContext(Dispatchers.IO) {
            productDao.addOrUpdate(listOf(product))
        }
    }

     fun getMaxId(): Long {
        return productDao.getMaxId()
     }

    fun getProductById(id: Long?): LiveData<Product> {
        return productDao.getProductById(id)
    }

    fun deleteProduct(id: Long) {
        productDao.deleteProduct(id)
    }

}