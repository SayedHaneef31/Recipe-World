package com.example.recipeworld

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeworld.Adapter.FoodVideoadapter
import com.example.recipeworld.Data.FoodVideo
import com.example.recipeworld.Instance.RetrofitInstance
import com.example.recipeworld.databinding.ActivityHomeBinding
import com.example.recipeworld.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding // Declare binding






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Initialize binding
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root) // Use binding.root to set the conten

        // Set up RecyclerView with horizontal scrolling for trending videos
        binding.recyclerTrending.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        // Fetch trending videos
        fetchTrendingVideos()


        // Update insets listener to use binding.main
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }
    private fun fetchTrendingVideos() {
        binding.progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.apiService.searchFoodVideos(
                    query = "chicken",
                    apiKey = RetrofitInstance.apiKey
                )

                val gson: Gson = GsonBuilder().setPrettyPrinting().create()
                Log.d("API_RESPONSE", gson.toJson(response))

                val url = "https://api.spoonacular.com/food/videos/search?query=pasta&number=10&apiKey=${RetrofitInstance.apiKey}"
                Log.d("API_URL", "Requesting: $url")

                binding.progressBar.visibility = View.GONE

                if (response.videos.isNotEmpty()) {
                    setupTrendingVideosAdapter(response.videos)
                } else {
                    Toast.makeText(this@HomeActivity,
                        "No trending videos found",
                        Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this@HomeActivity,
                    "Error: ${e.message}",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun setupTrendingVideosAdapter(videos: List<FoodVideo>) {
        val adapter = FoodVideoadapter(videos) { video ->
            playYoutubeVideo(video.youTubeId)
        }
        binding.recyclerTrending.adapter = adapter
    }
    private fun playYoutubeVideo(videoId: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=$videoId"))
        startActivity(intent)
    }
}