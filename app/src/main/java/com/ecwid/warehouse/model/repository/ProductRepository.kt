package com.ecwid.warehouse.model.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.ecwid.warehouse.model.dao.UserDao
import com.ecwid.warehouse.model.entity.Product

class ProductRepository(private val userDao: UserDao) {

    val data = userDao.findAll()

    suspend fun insert(user: Product) {
        withContext(Dispatchers.IO) {
            userDao.add(listOf(user))
        }
    }

     fun getMaxId(): Long {
        return userDao.getMaxId()
     }

    fun getProductById(id: Long?): LiveData<Product> {
        return userDao.getProductById(id)
    }


}