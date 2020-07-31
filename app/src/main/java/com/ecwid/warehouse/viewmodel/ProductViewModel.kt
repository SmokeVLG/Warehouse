package com.ecwid.warehouse.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ecwid.warehouse.model.repository.ProductRepository
import com.ecwid.warehouse.utils.LoadingState

class ProductViewModel(productRepository: ProductRepository) : ViewModel() {

    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState>
        get() = _loadingState

    val data = productRepository.data

  }