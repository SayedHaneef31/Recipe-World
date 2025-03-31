package com.example.recipeworld

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeworld.Adapter.RecipeAdapter
import com.example.recipeworld.Data.Recipe
import com.example.recipeworld.Instance.RetrofitInstance
import com.example.recipeworld.databinding.ActivitySearchBinding

import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding // Declare binding
    private val TAG = "SearchActivity" // Tag for logging
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewResults.layoutManager = LinearLayoutManager(this)

        setupSearch()


        // Update insets listener to use binding.main
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupSearch() {
        // Set click listener for search button
        binding.searchButton.setOnClickListener {
            Log.d(TAG, "Search button clicked")
            performSearch()
        }

        // Set up keyboard search action
        binding.searchIDDDD.setOnEditorActionListener { _, actionId, _ ->
            Log.d(TAG, "Editor action ID: $actionId")
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }
    private fun performSearch() {
        Log.d(TAG, "Performing search")
        //println("Performing search")
        val query = binding.searchIDDDD.text.toString().trim()
        Log.d(TAG, "Search query: $query")
        if (query.isEmpty()) {
            Toast.makeText(this, "Please enter a search term", Toast.LENGTH_SHORT).show()
            return
        }

        // Clear keyboard focus
        binding.searchIDDDD.clearFocus()

         //Showing loading indicator while fetching
         binding.progresssssssssss.visibility = View.VISIBLE

        lifecycleScope.launch {
            Log.d(TAG, "Making API call with query: $query")
            try {
                val response3 = RetrofitInstance.apiService.searchRecipes(
                    query = query,
                    number = 15, // Default as required
                    apiKey = RetrofitInstance.apiKey
                )
                binding.progresssssssssss.visibility = View.GONE

                Log.d(TAG, "API URL: ${RetrofitInstance.apiService.searchRecipes(query, 15, RetrofitInstance.apiKey).toString()}")
                Log.d(TAG, "API Response: ${response3.results}")

                // Hide loading indicator
                // binding.progressBar.visibility = View.GONE

                if (response3.results.isNotEmpty()) {
                    displaySearchResults(response3.results)
                } else {
                    Toast.makeText(
                        this@SearchActivity,
                        "No recipes found for '$query'",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Clear previous results if any
                    displaySearchResults(emptyList())
                }
            } catch (e: Exception) {
                // Hide loading indicator
                // binding.progressBar.visibility = View.GONE

                Toast.makeText(
                    this@SearchActivity,
                    "Error: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun displaySearchResults(recipes: List<Recipe>) {
        val adapter = RecipeAdapter(recipes) { recipe  ->
            var id=recipe.id
            val intent = Intent(this, RecipeDetailActivity::class.java)
            intent.putExtra("RECIPE_ID", id)
            startActivity(intent)
        }
        binding.recyclerViewResults.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewResults.adapter = adapter
    }

    // Helper method to hide the software keyboard
    private fun enableEdgeToEdge() {
        // Implement if needed
    }
}
