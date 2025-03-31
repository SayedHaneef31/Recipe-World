package com.example.recipeworld.Data

data class RecipeSearchResponse(
    val results: List<Recipe>,
    val totalResults: Int
)