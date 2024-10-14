package com.example.pizzaapp.ui

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.pizzaapp.R
import com.example.pizzaapp.databinding.FragmentProductDetailsBinding
import com.example.pizzaapp.model.productdetails.ProductDetails
import com.example.pizzaapp.model.settings.Settings
import com.example.pizzaapp.utils.Resources
import com.example.pizzaapp.viewmodel.ProductDetailsViewModel
import com.example.pizzaapp.viewmodel.ProductsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailsFragment : Fragment(R.layout.fragment_product_details) {
    private lateinit var binding: FragmentProductDetailsBinding

    private val productsDetailsViewModel  by viewModels<ProductDetailsViewModel>()
    private lateinit var productSettings :Settings

    private val REQUEST_CODE_CALL_PHONE = 1


    private lateinit var productDetails : ProductDetails

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductDetailsBinding.bind(view)



        binding.leftArrowIv.setOnClickListener {
            findNavController().navigateUp()
        }



        // getting id of product
        if (arguments != null){
            val product_id = requireArguments().getInt("product_id")


            // get product details from view model
            productsDetailsViewModel.getProductDetails(product_id)

           lifecycleScope.launch {
               productsDetailsViewModel.productDetails.collectLatest {
                   when(it){
                       is Resources.Error -> {

                       }
                       is Resources.Loading -> {

                       }
                       is Resources.Success -> {
                            it.data?.let {
                                productDetails = it
                                bindProductDetails(productDetails)
                            }?: Toast.makeText(requireContext(),"Failed To get Product details",Toast.LENGTH_SHORT).show()
                       }
                       is Resources.UnSpecified -> {

                       }
                   }
               }
           }
        }


        // get settings from api

       productsDetailsViewModel.getSettings()
        lifecycleScope.launch {
            productsDetailsViewModel.settings.collectLatest {
                productSettings = it
            }
        }


        // order food throw whatsapp
        binding.whatsappBtn.setOnClickListener {

            if (productsDetailsViewModel.isPackageInstalled(requireContext(),"com.whatsapp") ||
                productsDetailsViewModel.isPackageInstalled(requireContext(),"com.whatsapp.w4b")){

                val intent  =  Intent(Intent.ACTION_VIEW)
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=${productSettings.whatsapp}"+"&text="+""))
                startActivity(intent)
            }else{
                Toast.makeText(requireContext(),"Whats app is Not Installed ",Toast.LENGTH_SHORT).show()
            }
        }

        // order food throw phone call
        binding.callBtn.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(android.Manifest.permission.CALL_PHONE),
                    REQUEST_CODE_CALL_PHONE
                )
            } else {
                // Permission already granted, proceed with making the call
                val call_intent = Intent(Intent.ACTION_CALL)
                call_intent.data = Uri.parse("tel:${productSettings.phone}")
                startActivity(call_intent)
            }

        }

    }

    fun bindProductDetails(productDetails: ProductDetails){
        binding.productNameTv.text =productDetails.name
        binding.newPriceTv.text = (productDetails.price.toFloat() - productDetails.discount.toFloat() ).toString()
        binding.oldPriceTv.text = productDetails.price
        binding.productRb.rating = productDetails.rate.toFloat()
        binding.restaurantNameTv.text = productDetails.restaurant_name
        Glide.with(requireContext()).load(productDetails.product_image).into(binding.productImageIv)
        Glide.with(requireContext()).load(productDetails.restaurant_image).into(binding.restaurantImage)

        if (productDetails.favorite == 1){
            binding.iconBookmarkFilledWhiteIv.visibility =View.VISIBLE
        }else{
            binding.iconBookmarkFilledWhiteIv.visibility =View.INVISIBLE
        }

    }



}