package com.example.toyotasample.pojos
import com.google.gson.annotations.SerializedName
data class ProductList(
      @SerializedName("products") val products: List<Product>
)