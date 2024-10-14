package com.example.pizzaapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pizzaapp.R
import com.example.pizzaapp.adapters.BaseCategoryAdapter
import com.example.pizzaapp.databinding.ActivityMainBinding
import com.example.pizzaapp.databinding.FragmentBaseCategoryBinding
import com.example.pizzaapp.model.products.Products
import com.example.pizzaapp.utils.Resources
import com.example.pizzaapp.viewmodel.ProductsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
open class BaseCategoryFragment : Fragment(R.layout.fragment_base_category) {

     val productsViewModel by viewModels<ProductsViewModel>()
     lateinit var binding: FragmentBaseCategoryBinding
     lateinit var product :Products


    val productAdapter = BaseCategoryAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentBaseCategoryBinding.bind(view)

        setupRV()


        productAdapter.onProductClick={
            val bundle = Bundle().apply {
                putInt("product_id",it.id)
            }
            findNavController().navigate(R.id.action_homeFragment2_to_productDetailsFragment,bundle)
        }

        lifecycleScope.launch {

            productsViewModel.products.collectLatest {
                when(it){
                    is Resources.Error -> {
                        Toast.makeText(requireContext()," Error",Toast.LENGTH_SHORT).show()
                    }
                    is Resources.Loading -> {
                        Log.d("Loading","Loading State")
                    }
                    is Resources.Success -> {
                     productAdapter.differ.submitList(it.data)
                    }
                    is Resources.UnSpecified -> {
                    }
                }
            }
        }
    }


    private fun setupRV(){

        binding.productsRv.adapter = productAdapter
        binding.productsRv.layoutManager = GridLayoutManager(requireContext(),2)
    }
}