package com.example.recipeworld.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeworld.Data.FoodVideo
import com.example.recipeworld.R

class FoodVideoadapter(
    private val videos: List<FoodVideo>,
    private val onVideoClick: (FoodVideo) -> Unit
): RecyclerView.Adapter<FoodVideoadapter.VideoViewHolder>() {

    class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val videoThumbnail: ImageView = itemView.findViewById(R.id.videoThumbnail)
        val videoDuration: TextView = itemView.findViewById(R.id.videoDuration)
        val videoTitle: TextView = itemView.findViewById(R.id.videoTitle)
        val videoViews: TextView = itemView.findViewById(R.id.videoViews)
        val videoRating: TextView= itemView.findViewById(R.id.videoRating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view=LayoutInflater.from(parent.context)
            .inflate(R.layout.item_food_video,parent,false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = videos[position]
        holder.videoTitle.text = video.title

        // Convert length (seconds) to format MM:SS
        val minutes = video.length / 60
        val seconds = video.length % 60
        holder.videoDuration.text = String.format("%d:%02d", minutes, seconds)

        // Format views count (e.g., 550K)
        val formattedViews = when {
            video.views >= 1_000_000 -> String.format("%.1fM views", video.views / 1_000_000f)
            video.views >= 1_000 -> String.format("%.1fK views", video.views / 1_000f)
            else -> "${video.views} views"
        }
        holder.videoViews.text = formattedViews

        // Format rating as percentage
        val ratingPercent = (video.rating * 100).toInt()
        holder.videoRating.text = "$ratingPercent%"

        // Load the thumbnail image
        Glide.with(holder.itemView.context)
            .load(video.thumbnail)
            .centerCrop()
            .into(holder.videoThumbnail)

        // Set click listener for the item
        holder.itemView.setOnClickListener {
            onVideoClick(video)
        }
    }
    override fun getItemCount() = videos.size
}