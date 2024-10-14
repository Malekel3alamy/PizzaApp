package com.example.pizzaapp.model.categories

data class CategoriesResponse(
    val `data`: MutableList<Category>,
    val status: Boolean
)