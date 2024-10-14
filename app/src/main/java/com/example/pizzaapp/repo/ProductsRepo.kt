package com.example.pizzaapp.repo

import com.example.pizzaapp.api.ProductsApi
import com.example.pizzaapp.api.RetrofitInstance
import com.example.pizzaapp.model.categories.CategoriesResponse
import com.example.pizzaapp.model.productdetails.ProductDetailsResponse
import com.example.pizzaapp.model.products.ProductsResponse
import com.example.pizzaapp.model.settings.SettingsResponse
import retrofit2.Response

class ProductsRepo() {

    suspend fun getProducts():Response<ProductsResponse> =   RetrofitInstance.api.getProducts()

    suspend fun getCategories ():Response<CategoriesResponse> =RetrofitInstance.api.getCategories()

    suspend fun getProductDetails(id:Int):ProductDetailsResponse = RetrofitInstance.api.getProductDetails(id)

    suspend fun getSettings():SettingsResponse = RetrofitInstance.api.getSettings()


}