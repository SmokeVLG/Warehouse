package com.ecwid.warehouse.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecwid.warehouse.model.entity.Product
import com.ecwid.warehouse.model.repository.ProductRepository
import com.ecwid.warehouse.utils.LoadingState
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AddProductViewModel(private val productRepository: ProductRepository) : ViewModel() {

    fun insertUser(product: Product) {

        viewModelScope.launch {
            try {
                _loadingState.value = LoadingState.LOADING
                productRepository.insert(product)
                _loadingState.value = LoadingState.LOADED
            } catch (e: Exception) {
                _loadingState.value = LoadingState.error(e.message)
            }
        }
    }

    fun getMaxId(): Long {
        return runBlocking {
            val asyncResult = async {
                productRepository.getMaxId()
            }
            return@runBlocking asyncResult.await()
        }
    }

    private val _loadingState = MutableLiveData<LoadingState>()

}


