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
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.foodorderingapp.R
import com.example.foodorderingapp.data.remote.RetrofitClient
import com.example.foodorderingapp.databinding.FragmentHomeBinding
import com.example.foodorderingapp.ui.adapter.FoodAdapter
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = view.findViewById(R.id.recyclerFoods)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), calculateNoOfColumns(requireContext(), 180f))
        Log.w("DENEME", "AÇILDI")
        binding = FragmentHomeBinding.inflate(inflater,container, false)

        binding.btnCart.setOnClickListener {
            findNavController().navigate(R.id.cartFragment)
        }

        fetchFoodList()

        return view
    }

    private fun fetchFoodList() {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.getAllFoods()
                Log.w("DENEME", "$response")

                recyclerView.adapter = FoodAdapter(response.yemekler) { food ->
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

    fun calculateNoOfColumns(context: Context, columnWidthDp: Float): Int {
        val displayMetrics = context.resources.displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        return (screenWidthDp / columnWidthDp + 0.5).toInt()
    }

}
