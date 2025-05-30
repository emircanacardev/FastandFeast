package com.example.foodorderingapp.ui.view

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.foodorderingapp.data.model.Food
import com.example.foodorderingapp.data.remote.RetrofitClient
import com.example.foodorderingapp.databinding.FragmentDetailBinding
import kotlinx.coroutines.launch

class DetailFragment : Fragment() {

    private lateinit var selectedFood: Food
    private var quantity = 1

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        setupUI()
        setupListeners()
        return binding.root
    }

    private fun setupUI() {
        binding.txtDetailName.text = selectedFood.yemek_adi
        binding.txtDetailPrice.text = "₺ ${selectedFood.yemek_fiyat}"
        binding.textViewQuantity.text = quantity.toString()
        updateTotalPrice()

        Glide.with(requireContext())
            .load("http://kasimadalan.pe.hu/yemekler/resimler/${selectedFood.yemek_resim_adi}")
            .into(binding.imgDetailFood)
    }

    private fun setupListeners() {
        binding.fabDecrease.setOnClickListener {
            if (quantity > 1) {
                quantity--
                binding.textViewQuantity.text = quantity.toString()
                updateTotalPrice()
            }
        }

        binding.fabIncrease.setOnClickListener {
            quantity++
            binding.textViewQuantity.text = quantity.toString()
            updateTotalPrice()
        }

        binding.btnAddToCart.setOnClickListener {
            addToCart()
        }

        binding.fabClose.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun updateTotalPrice() {
        val total = selectedFood.yemek_fiyat.toIntOrNull()?.times(quantity) ?: 0
        binding.textViewQuantityPrice.text = "₺ $total"
    }

    private fun addToCart() {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.addToCart(
                    yemekAdi = selectedFood.yemek_adi,
                    yemekResimAdi = selectedFood.yemek_resim_adi,
                    yemekFiyat = selectedFood.yemek_fiyat.toInt(),
                    yemekAdet = quantity,
                    kullaniciAdi = "emircanacar"
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
