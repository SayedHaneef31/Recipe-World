package com.example.recipeworld.Adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeworld.Data.Restaurant
import com.example.recipeworld.R

class RestaurantAdapter (private val restaurantList: List<Restaurant>) :
    RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>()
{
    class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tvRestaurantName)
        val phone: TextView = itemView.findViewById(R.id.tvPhoneNumber)
        val address: TextView = itemView.findViewById(R.id.tvAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.restaurant_card, parent, false)
        return RestaurantViewHolder(view)
    }

    override fun getItemCount(): Int = restaurantList.size

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = restaurantList[position]

        holder.name.text = restaurant.name
        holder.phone.text = "Phone: ${restaurant.phoneNumber}"
        holder.address.text = restaurant.address.street

        // Make phone number clickable to call
        holder.phone.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${restaurant.phoneNumber}")
            it.context.startActivity(intent)
        }
    }
}