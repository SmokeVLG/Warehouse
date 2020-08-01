package com.ecwid.warehouse.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ecwid.warehouse.model.repository.ProductRepository
import com.ecwid.warehouse.utils.LoadingState

class ProductsViewModel(productRepository: ProductRepository) : ViewModel() {

    val data = productRepository.data

  }