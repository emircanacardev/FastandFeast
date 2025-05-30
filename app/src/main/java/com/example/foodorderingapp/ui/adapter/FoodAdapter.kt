package com.example.foodorderingapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorderingapp.R
import com.example.foodorderingapp.data.model.Food
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FoodAdapter(
    private val items: List<Food>,
    private val onItemClick: (Food) -> Unit,
    private val onAddToCartClick: (Food) -> Unit
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    inner class FoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgFood: ImageView = view.findViewById(R.id.imgFood)
        val txtFoodName: TextView = view.findViewById(R.id.txtFoodName)
        val txtFoodPrice: TextView = view.findViewById(R.id.txtFoodPrice)
        val fabAddToCart: FloatingActionButton = view.findViewById(R.id.fabAddToCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = items[position]

        holder.txtFoodName.text = food.yemek_adi
        holder.txtFoodPrice.text = "₺${food.yemek_fiyat}"

        Glide.with(holder.itemView.context)
            .load("http://kasimadalan.pe.hu/yemekler/resimler/${food.yemek_resim_adi}")
            .into(holder.imgFood)

        holder.itemView.setOnClickListener {
            onItemClick(food)
        }

        holder.fabAddToCart.setOnClickListener {
            onAddToCartClick(food)
        }
    }
}
