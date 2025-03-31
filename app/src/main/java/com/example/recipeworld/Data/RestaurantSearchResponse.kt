package com.example.recipeworld.Data

data class RestaurantSearchResponse(
    val restaurants: List<Restaurant>,
    val totalResults: Int
)