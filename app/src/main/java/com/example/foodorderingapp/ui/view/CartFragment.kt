package com.example.foodorderingapp.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderingapp.data.model.CartItem
import com.example.foodorderingapp.data.remote.RetrofitClient
import com.example.foodorderingapp.databinding.FragmentCartBinding
import com.example.foodorderingapp.ui.adapter.CartAdapter
import kotlinx.coroutines.launch

class CartFragment : Fragment() {private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private lateinit var cartAdapter: CartAdapter
    private var cartItems = mutableListOf<CartItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerCart.layoutManager = LinearLayoutManager(requireContext())
        cartAdapter = CartAdapter(cartItems, ::removeFromCart)
        binding.recyclerCart.adapter = cartAdapter

        loadCart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadCart() {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.getCart("emircan")
                cartItems.clear()
                cartItems.addAll(response.sepet_yemekler)
                cartAdapter.notifyDataSetChanged()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Cart could not be loaded", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun removeFromCart(sepet_yemek_id: Int) {
        lifecycleScope.launch {
            try {
                RetrofitClient.api.deleteCartItem(sepet_yemek_id, "emircan")
                Toast.makeText(requireContext(), "Item removed", Toast.LENGTH_SHORT).show()
                loadCart()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Delete failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
