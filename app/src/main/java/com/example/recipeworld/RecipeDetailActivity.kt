package com.example.recipeworld

import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.recipeworld.Instance.RetrofitInstance
import com.example.recipeworld.databinding.ActivityRecipeDetailBinding
import kotlinx.coroutines.launch

class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val recipeId = intent.getIntExtra("RECIPE_ID", -1)
        if (recipeId != -1) {
            loadRecipeSummary(recipeId)
        } else {
            Toast.makeText(this, "Recipe ID not found", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
    private fun loadRecipeSummary(recipeId: Int) {
        // Show loading indicator
        binding.progressBar.visibility = View.VISIBLE
        binding.summaryText.visibility = View.GONE

        lifecycleScope.launch {
            try {
                val recipeSummary = RetrofitInstance.apiService.getRecipeSummary(
                    id = recipeId,
                    apiKey = RetrofitInstance.apiKey
                )

                // Hide loading indicator
                binding.progressBar.visibility = View.GONE
                binding.summaryText.visibility = View.VISIBLE

                // Display the summary (remove HTML tags if needed)
                binding.summaryText.text = Html.fromHtml(recipeSummary.summary, Html.FROM_HTML_MODE_COMPACT)
                binding.titleText.text = recipeSummary.title

            } catch (e: Exception) {
                // Hide loading indicator
                binding.progressBar.visibility = View.GONE

                Toast.makeText(
                    this@RecipeDetailActivity,
                    "Error: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}