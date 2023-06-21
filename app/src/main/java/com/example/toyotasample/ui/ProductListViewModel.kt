package com.example.toyotasample.ui
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.toyotasample.network.ProdListServiceApi
import com.example.toyotasample.network.RetrofitClient
import com.example.toyotasample.pojos.Product

import kotlinx.coroutines.*

class ProductListViewModel : ViewModel() {
    val productServiceApi = RetrofitClient.getInstance().create(ProdListServiceApi::class.java)
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val products = MutableLiveData<List<Product>>()
    val productsLoadError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()
    fun refresh() {
        fetchProducts()
    }
    private fun fetchProducts() {
        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = productServiceApi.getAllProducts()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    products.value = response.body()?.products
                    productsLoadError.value = null
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
        productsLoadError.value = ""
        loading.value = false
    }
    private fun onError(message: String) {
        productsLoadError.value = message
        loading.value = false
    }
    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
} 