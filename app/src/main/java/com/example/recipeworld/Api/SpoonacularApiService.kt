package com.example.recipeworld.Api

import com.example.recipeworld.Data.FoodVideoResponse
import com.example.recipeworld.Data.RestaurantSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SpoonacularApiService
{
    @GET("food/videos/search")
    suspend fun searchFoodVideos(     //this makes the function compatible with Kotlin Coroutines, Instead of returning a Call<FoodVideoResponse>, it directly returns FoodVideoResponse (simplifying network calls).
        @Query("query") query: String="pasta",
        @Query("number") number: Int=10,
        @Query("apiKey") apiKey: String,

    ) :FoodVideoResponse    // Expected response

    @GET("food/restaurants/search")
    suspend fun searchRestaurants(     //this makes the function compatible with Kotlin Coroutines, Instead of returning a Call<FoodVideoResponse>, it directly returns FoodVideoResponse (simplifying network calls).
        @Query("lat") latitude:Double=40.476139,
        @Query("lng") longitude:Double=-79.756995,
        @Query("distance") distance: Int=2,
        @Query("apiKey") apiKey: String,

        ) : RestaurantSearchResponse    // Expected response

}