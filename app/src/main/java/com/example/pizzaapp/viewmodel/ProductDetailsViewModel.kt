package com.example.pizzaapp.viewmodel

import android.content.Context
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizzaapp.model.productdetails.ProductDetails
import com.example.pizzaapp.model.productdetails.ProductDetailsResponse
import com.example.pizzaapp.model.settings.Settings
import com.example.pizzaapp.repo.ProductsRepo
import com.example.pizzaapp.utils.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel  @Inject constructor(private val productsRepo: ProductsRepo): ViewModel() {

    var ProductDetailsResponse : ProductDetailsResponse? = null

    val settings = MutableSharedFlow<Settings>()

    private val _productDetails = MutableSharedFlow<Resources<ProductDetails>>()
    val productDetails = _productDetails.asSharedFlow()

    fun getProductDetails(productId: Int) =viewModelScope.launch {
        _productDetails.emit(Resources.Loading())

        val response = productsRepo.getProductDetails(productId)
        // val result = handleProductsResponse(response)
        _productDetails.emit(Resources.Success(response.data))

    }




    fun getSettings()=
        viewModelScope.launch {
            val response = productsRepo.getSettings()
            response.data?.let {
                settings.emit(it)
            }
        }

    // make sure whatsapp package is installed on user phone
    fun isPackageInstalled(context: Context, packagename: String?): Boolean {
        var result = true
        try {
            // is the application installed?
            context.packageManager.getPackageInfo(packagename!!, PackageManager.GET_ACTIVITIES)
        } catch (e: PackageManager.NameNotFoundException) {
            result = false

        }
        return result
    }
}