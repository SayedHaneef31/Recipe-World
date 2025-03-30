package com.example.recipeworld.Instance

import com.example.recipeworld.Api.SpoonacularApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance   //its a singleton object it ensure that only one object of retrofit is created
{
    private const val BASE_URL= "https://api.spoonacular.com/"
    private const val API_KEY= "696328ecc7a54de0ab8c86e1828aee7f"

    private val retrofit by lazy {    //by lazy {} ensures Retrofit is only initialized when first accessed.
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    //Creates an instance of SpoonacularApiService, which contains API endpoints.
    //Allows us to make API calls easily using apiService.searchFoodVideos(...).
    val apiService: SpoonacularApiService by lazy {
        retrofit.create(SpoonacularApiService::class.java)
    }
    //Exposes API_KEY as a public variable, so it can be accessed from outside.
    val apiKey: String = API_KEY
}