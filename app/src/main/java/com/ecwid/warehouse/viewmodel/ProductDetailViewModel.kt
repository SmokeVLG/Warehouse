package com.ecwid.warehouse.viewmodel

import androidx.lifecycle.*
import com.ecwid.warehouse.model.entity.Product
import com.ecwid.warehouse.model.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductDetailViewModel  constructor(
    private val repository: ProductRepository
) : ViewModel() {
    private val _id = MutableLiveData<Long>()

    private val _product = _id.switchMap { id ->
        repository.getProductById(id)
    }
    val product: LiveData<Product> = _product


    fun start(id: Long) {
        _id.value = id
    }

    fun deleteProduct() {
        viewModelScope.launch {
            repository.deleteProduct(_id.value!!)
        }
    }
}
