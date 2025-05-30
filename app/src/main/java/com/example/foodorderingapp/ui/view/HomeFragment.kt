package com.example.foodorderingapp.ui.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodorderingapp.R
import com.example.foodorderingapp.data.remote.RetrofitClient
import com.example.foodorderingapp.databinding.FragmentHomeBinding
import com.example.foodorderingapp.ui.adapter.FoodAdapter
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.recyclerFoods.layoutManager =
            GridLayoutManager(requireContext(), calculateNoOfColumns(requireContext(), 180f))

        binding.btnCart.setOnClickListener {
            findNavController().navigate(R.id.cartFragment)
        }

        fetchFoodList()

        return binding.root
    }

    private fun fetchFoodList() {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.getAllFoods()
                Log.w("DENEME", "$response")

                binding.recyclerFoods.adapter = FoodAdapter(response.yemekler) { food ->
                    val bundle = Bundle().apply {
                        putSerializable("selectedFood", food)
                    }
                    findNavController().navigate(R.id.detailFragment, bundle)
                }

            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show()
                Log.w("DENEME", "$e")
            }
        }
    }

    private fun calculateNoOfColumns(context: Context, columnWidthDp: Float): Int {
        val displayMetrics = context.resources.displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        return (screenWidthDp / columnWidthDp + 0.5).toInt()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
