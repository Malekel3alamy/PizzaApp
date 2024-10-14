package com.example.pizzaapp.model.productdetails

data class ProductDetails(
    val discount: String,
    val favorite: Int,
    val id: Int,
    val name: String,
    val price: String,
    val product_image: String,
    val rate: String,
    val restaurant_id: Int,
    val restaurant_image: String,
    val restaurant_name: String
)