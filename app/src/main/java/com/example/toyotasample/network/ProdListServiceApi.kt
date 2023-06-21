package com.example.toyotasample.network

import com.example.toyotasample.pojos.Product
import com.example.toyotasample.pojos.ProductList
import retrofit2.Response
import retrofit2.http.GET

interface ProdListServiceApi {

@GET("/products")
suspend fun getAllProducts(): Response<ProductList>

}