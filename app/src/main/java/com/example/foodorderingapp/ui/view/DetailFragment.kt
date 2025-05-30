package com.example.foodorderingapp.ui.view

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.foodorderingapp.R
import com.example.foodorderingapp.data.model.Food
import com.example.foodorderingapp.data.remote.RetrofitClient
import kotlinx.coroutines.launch

class DetailFragment : Fragment() {

    private lateinit var selectedFood: Food
    private var quantity = 1

    private lateinit var txtName: TextView
    private lateinit var txtPrice: TextView
    private lateinit var txtQuantity: TextView
    private lateinit var txtTotalPrice: TextView
    private lateinit var imgFood: ImageView
    private lateinit var btnAddToCart: Button
    private lateinit var btnDecrease: View
    private lateinit var btnIncrease: View
    private lateinit var btnClose: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedFood = it.getSerializable("selectedFood") as Food
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        txtName = view.findViewById(R.id.txtDetailName)
        txtPrice = view.findViewById(R.id.txtDetailPrice)
        txtQuantity = view.findViewById(R.id.textViewQuantity)
        txtTotalPrice = view.findViewById(R.id.textViewQuantityPrice)
        imgFood = view.findViewById(R.id.imgDetailFood)
        btnAddToCart = view.findViewById(R.id.btnAddToCart)
        btnDecrease = view.findViewById(R.id.fabDecrease)
        btnIncrease = view.findViewById(R.id.fabIncrease)
        btnClose = view.findViewById(R.id.fabClose)

        setupUI()
        setupListeners()

        return view
    }

    private fun setupUI() {
        txtName.text = selectedFood.yemek_adi
        txtPrice.text = "₺ ${selectedFood.yemek_fiyat}"
        txtQuantity.text = quantity.toString()
        updateTotalPrice()

        Glide.with(requireContext())
            .load("http://kasimadalan.pe.hu/yemekler/resimler/${selectedFood.yemek_resim_adi}")
            .into(imgFood)
    }

    private fun setupListeners() {
        btnDecrease.setOnClickListener {
            if (quantity > 1) {
                quantity--
                txtQuantity.text = quantity.toString()
                updateTotalPrice()
            }
        }

        btnIncrease.setOnClickListener {
            quantity++
            txtQuantity.text = quantity.toString()
            updateTotalPrice()
        }

        btnAddToCart.setOnClickListener {
            addToCart()
        }

        btnClose.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun updateTotalPrice() {
        val total = selectedFood.yemek_fiyat.toIntOrNull()?.times(quantity) ?: 0
        txtTotalPrice.text = "₺ $total"
    }

    private fun addToCart() {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.addToCart(
                    yemekAdi = selectedFood.yemek_adi,
                    yemekResimAdi = selectedFood.yemek_resim_adi,
                    yemekFiyat = selectedFood.yemek_fiyat.toInt(),
                    yemekAdet = quantity,
                    kullaniciAdi = "emircanacar" // KULLANICI ADI
                )
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Added to cart!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Failed to add to cart!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

}
