package com.example.foodorderingapp.ui.adapter

import com.example.foodorderingapp.R

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorderingapp.data.model.CartItem

class CartAdapter(
    private val items: List<CartItem>,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.textCartName)
        val quantity: TextView = view.findViewById(R.id.textCartQuantity)
        val price: TextView = view.findViewById(R.id.textCartPrice)
        val image: ImageView = view.findViewById(R.id.imageCart)
        val deleteBtn: ImageButton = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = items[position]

        holder.name.text = item.yemek_adi
        holder.quantity.text = "${item.yemek_siparis_adet} pcs"

        // Fiyat ve adet String olduğu için önce Int'e çeviriyoruz
        val adet = item.yemek_siparis_adet.toIntOrNull() ?: 0
        val fiyat = item.yemek_fiyat.toIntOrNull() ?: 0
        holder.price.text = "₺${fiyat * adet}"

        Glide.with(holder.itemView.context)
            .load("http://kasimadalan.pe.hu/yemekler/resimler/${item.yemek_resim_adi}")
            .into(holder.image)

        holder.deleteBtn.setOnClickListener {
            val sepetYemekId = item.sepet_yemek_id.toIntOrNull()
            if (sepetYemekId != null) {
                onDeleteClick(sepetYemekId)
            }
        }
    }
}
