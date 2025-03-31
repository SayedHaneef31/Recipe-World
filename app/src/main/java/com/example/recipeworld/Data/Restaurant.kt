package com.example.recipeworld.Data

data class Restaurant(
    val name: String,
    val phoneNumber: String?,
    val address: Address // ✅ Now correctly represents the nested object
)