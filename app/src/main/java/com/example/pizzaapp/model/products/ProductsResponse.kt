package com.example.pizzaapp.model.products

data class ProductsResponse(
    val `data`: MutableList<Products>,
    val status: Boolean
)