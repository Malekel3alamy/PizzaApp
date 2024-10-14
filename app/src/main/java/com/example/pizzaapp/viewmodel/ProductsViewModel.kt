package com.example.pizzaapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizzaapp.model.productdetails.ProductDetails
import com.example.pizzaapp.model.productdetails.ProductDetailsResponse
import com.example.pizzaapp.model.products.Products
import com.example.pizzaapp.model.products.ProductsResponse
import com.example.pizzaapp.model.settings.Settings
import com.example.pizzaapp.repo.ProductsRepo
import com.example.pizzaapp.utils.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(private val productsRepo: ProductsRepo): ViewModel() {



    var ProductResponse : ProductsResponse? = null
    var ProductDetailsResponse : ProductDetailsResponse? = null


    val settings = MutableSharedFlow<Settings>()


    private val _products = MutableSharedFlow<Resources<List<Products>>>()
    val products = _products.asSharedFlow()

    private val _productDetails = MutableSharedFlow<Resources<ProductDetails>>()
    val productDetails = _productDetails.asSharedFlow()

init {
    Log.d("VIEW_MODEL","getProducts")
    getProducts()
}

   private fun getProducts() =viewModelScope.launch {
    _products.emit(Resources.Loading())
       Log.d("VIEW_MODEL","getProducts inside fun")
        val response = handleProductsResponse(productsRepo.getProducts())

        response.data?.let {
            _products.emit(Resources.Success(data = it.data))
            Log.d("VIEW_MODEL","getProducts succeeded")
        }?: viewModelScope.launch {
            _products.emit(Resources.Error(response.message.toString()))
            Log.d("VIEW_MODEL","getProducts failed")
        }


    }

    // to turn response into resources
  private  fun handleProductsResponse(
        response : Response<ProductsResponse>
    ) : Resources<ProductsResponse> {

        if(response.isSuccessful){
            response.body()?.let{ resultResponse ->


                if(ProductResponse == null){
                    Log.d("VIEW_MODEL","result response")

                    ProductResponse = resultResponse
                }else{
                    val oldArticles = ProductResponse?.data
                    val newArticles = resultResponse.data
                    oldArticles?.addAll(newArticles)

                }
                return Resources.Success(ProductResponse?:resultResponse)

            }
        }

        return Resources.Error(response.message())

    }

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





  /*  // to turn response into resources
    private  fun handleProductDetailsResponse(
        response : Response<ProductDetailsResponse>
    ) : Resources<ProductDetailsResponse> {

        if(response.isSuccessful){
            response.body()?.let{ resultResponse ->


                if(ProductDetailsResponse == null){
                    Log.d("VIEW_MODEL","result response")

                    ProductDetailsResponse = resultResponse
                }else{
                    val oldArticles = ProductDetailsResponse?.data
                    val newArticles = resultResponse.data
                    oldArticles?.addAll(newArticles)

                }
                return Resources.Success(ProductResponse?:resultResponse)

            }
        }

        return Resources.Error(response.message())

    }*/


}