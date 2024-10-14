package com.example.pizzaapp.api

import com.example.pizzaapp.model.categories.CategoriesResponse
import com.example.pizzaapp.model.productdetails.ProductDetailsResponse
import com.example.pizzaapp.model.products.ProductsResponse
import com.example.pizzaapp.model.settings.SettingsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ProductsApi {

    @GET("products.php")
    suspend fun getProducts():Response<ProductsResponse>

   @GET("categories.php")
   suspend fun getCategories():Response<CategoriesResponse>

    @GET("product_details.php")
    suspend fun getProductDetails(
        @Query("id")
          id:Int
    ):ProductDetailsResponse

    @GET("settings.php")
    suspend fun getSettings(

    ):SettingsResponse





}