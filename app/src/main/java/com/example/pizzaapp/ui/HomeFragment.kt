package com.example.pizzaapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.pizzaapp.R
import com.example.pizzaapp.adapters.HomeViewPagerAdapter
import com.example.pizzaapp.databinding.FragmentHomeBinding
import com.example.pizzaapp.helpers.ViewPager2ViewHeightAnimator
import com.example.pizzaapp.model.categories.Category
import com.example.pizzaapp.utils.Resources
import com.example.pizzaapp.viewmodel.ProductsViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding : FragmentHomeBinding

    private val productsViewModel by viewModels<ProductsViewModel>()

    private var categoriesList = mutableListOf<Category>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        productsViewModel.getCategories()
             // getting categories from api
        lifecycleScope.launch {
            productsViewModel.categories.collectLatest {
                when(it){
                    is Resources.Error -> {

                       Toast.makeText(requireContext()," Failed to get Categories",Toast.LENGTH_SHORT).show()
                    }
                    is Resources.Loading -> {

                    }
                    is Resources.Success -> {
                     Log.d("return","success")
                        it.data?.let {categories ->
                            categoriesList.addAll(categories)
                            Log.d("Categories",categories.toString())


                            // setup TabLayout and ViewPager
                            binding.viewPagerHome.adapter = HomeViewPagerAdapter(requireActivity())

                            TabLayoutMediator(binding.tabLayout,binding.viewPagerHome){tab,position ->
                                when(position){
                                    0 -> { tab.text = categoriesList[0].name
                                    }
                                    1 -> tab.text = categoriesList[1].name
                                    2 -> tab.text = categoriesList[2].name
                                    3 -> tab.text = categoriesList[3].name
                                    4 -> tab.text = categoriesList[4].name
                                    5 -> tab.text = categoriesList[5].name
                                    6 -> tab.text = categoriesList[6].name
                                    7 -> tab.text = categoriesList[7].name
                                    8 -> tab.text = categoriesList[8].name
                                    9 -> tab.text = categoriesList[9].name
                                }

                            }.attach()
                        }

                    }
                    is Resources.UnSpecified -> {

                    }
                }
            }
        }



    }



}